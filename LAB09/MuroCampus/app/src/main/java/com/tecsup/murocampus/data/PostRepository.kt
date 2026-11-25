package com.tecsup.murocampus.data

import com.tecsup.murocampus.data.local.PostDao
import com.tecsup.murocampus.data.local.PostEntity
import com.tecsup.murocampus.data.local.aEntidad
import com.tecsup.murocampus.data.local.aPost
import com.tecsup.murocampus.data.remote.PostApi
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.EdicionPost
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

interface PostRepository {
    suspend fun listar(): Result<List<Post>>
    suspend fun pagina(inicio: Int, limite: Int): Result<List<Post>>
    suspend fun detalle(id: Int): Result<Post>
    suspend fun comentarios(id: Int): Result<List<Comentario>>
    suspend fun porUsuario(userId: Int): Result<List<Post>>
    suspend fun crear(nuevo: NuevoPost): Result<Post>
    suspend fun borrar(id: Int): Result<Unit>
    suspend fun editarTitulo(id: Int, titulo: String): Result<Post>
}

/**
 * Pide los datos a la red y guarda las publicaciones en Room.
 * Si no hay conexión (IOException), decide usar las publicaciones guardadas.
 */
class PostRepositoryRemote(
    private val api: PostApi,
    private val dao: PostDao
) : PostRepository {

    override suspend fun listar(): Result<List<Post>> =
        redOCache(red = { api.listar() }, local = { dao.todos() })

    override suspend fun pagina(inicio: Int, limite: Int): Result<List<Post>> =
        redOCache(red = { api.pagina(inicio, limite) }, local = { dao.pagina(inicio, limite) })

    override suspend fun detalle(id: Int): Result<Post> = llamar { api.detalle(id) }

    override suspend fun comentarios(id: Int): Result<List<Comentario>> =
        llamar { api.comentarios(id) }

    override suspend fun porUsuario(userId: Int): Result<List<Post>> =
        redOCache(red = { api.porUsuario(userId) }, local = { dao.porUsuario(userId) })

    override suspend fun crear(nuevo: NuevoPost): Result<Post> = llamar { api.crear(nuevo) }

    override suspend fun borrar(id: Int): Result<Unit> = llamar {
        val respuesta = api.borrar(id)
        // Con Response<Unit> Retrofit no lanza la excepción: hay que revisar el código.
        if (!respuesta.isSuccessful) throw HttpException(respuesta)
        dao.borrar(id)
    }

    override suspend fun editarTitulo(id: Int, titulo: String): Result<Post> = llamar {
        api.editar(id, EdicionPost(title = titulo)).also { dao.actualizarTitulo(id, it.title) }
    }

    // Primero la red (y se guarda en Room). Sin conexión, lo guardado si hay algo.
    private suspend fun redOCache(
        red: suspend () -> List<Post>,
        local: suspend () -> List<PostEntity>
    ): Result<List<Post>> {
        val resultado = llamar { red().also { posts -> dao.guardar(posts.map { it.aEntidad() }) } }
        if (resultado.exceptionOrNull() is IOException) {
            val guardados = llamar { local().map { it.aPost() } }.getOrNull()
            if (!guardados.isNullOrEmpty()) return Result.success(guardados)
        }
        return resultado
    }

    private suspend fun <T> llamar(bloque: suspend () -> T): Result<T> =
        try {
            Result.success(bloque())
        } catch (e: CancellationException) {
            throw e // nunca se traga la cancelación
        } catch (e: Exception) {
            Result.failure(e)
        }
}
