package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.murocampus.data.PostRepository

class PostsViewModelFactory(
    private val repo: PostRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            "ViewModel no soportado: ${modelClass.name}"
        }
        return PostsViewModel(repo) as T
    }
}
