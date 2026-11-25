package com.tecsup.murocampus.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {

    @Upsert
    suspend fun guardar(posts: List<PostEntity>)

    @Query("SELECT * FROM posts ORDER BY id")
    suspend fun todos(): List<PostEntity>

    @Query("SELECT * FROM posts ORDER BY id LIMIT :limite OFFSET :inicio")
    suspend fun pagina(inicio: Int, limite: Int): List<PostEntity>

    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY id")
    suspend fun porUsuario(userId: Int): List<PostEntity>

    @Query("UPDATE posts SET title = :titulo WHERE id = :id")
    suspend fun actualizarTitulo(id: Int, titulo: String)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun borrar(id: Int)
}
