package com.tecsup.murocampus.data.remote

import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.EdicionPost
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    suspend fun listar(): List<Post>

    // Paginación: desde la posición _start, como máximo _limit publicaciones.
    @GET("posts")
    suspend fun pagina(@Query("_start") inicio: Int, @Query("_limit") limite: Int): List<Post>

    @GET("posts/{id}")
    suspend fun detalle(@Path("id") id: Int): Post

    @GET("posts/{id}/comments")
    suspend fun comentarios(@Path("id") id: Int): List<Comentario>

    @GET("posts")
    suspend fun porUsuario(@Query("userId") userId: Int): List<Post>

    @POST("posts")
    suspend fun crear(@Body nuevo: NuevoPost): Post

    // PATCH modifica solo los campos enviados (aquí, el título).
    @PATCH("posts/{id}")
    suspend fun editar(@Path("id") id: Int, @Body cambio: EdicionPost): Post

    // Response<Unit>: la respuesta viene vacía, solo interesa el código.
    @DELETE("posts/{id}")
    suspend fun borrar(@Path("id") id: Int): Response<Unit>
}
