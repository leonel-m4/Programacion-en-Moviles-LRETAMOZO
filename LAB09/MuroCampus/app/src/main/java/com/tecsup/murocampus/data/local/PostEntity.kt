package com.tecsup.murocampus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tecsup.murocampus.model.Post

/** Una publicación guardada en el teléfono. */
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun Post.aEntidad() = PostEntity(id = id, userId = userId, title = title, body = body)

fun PostEntity.aPost() = Post(userId = userId, id = id, title = title, body = body)
