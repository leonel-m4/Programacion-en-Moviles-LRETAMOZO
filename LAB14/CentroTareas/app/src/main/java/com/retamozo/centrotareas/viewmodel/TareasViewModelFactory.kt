package com.retamozo.centrotareas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.retamozo.centrotareas.data.TareasRepository

class TareasViewModelFactory(
    private val repo: TareasRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(TareasViewModel::class.java)) {
            "ViewModel no soportado: ${modelClass.name}"
        }
        return TareasViewModel(repo) as T
    }
}
