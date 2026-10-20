package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ErroresTicket(
    val categoria: String? = null,
    val titulo: String? = null,
    val descripcion: String? = null
)

class NuevoTicketViewModel(
    private val repo: AyudaRepository,
    private val clienteId: Int
) : ViewModel() {

    val categorias: StateFlow<List<Categoria>> = repo.observarCategoriasActivas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _categoriaId = MutableStateFlow<Int?>(null)
    val categoriaId: StateFlow<Int?> = _categoriaId.asStateFlow()

    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _ubicacion = MutableStateFlow("")
    val ubicacion: StateFlow<String> = _ubicacion.asStateFlow()

    private val _prioridad = MutableStateFlow(Prioridad.MEDIA)
    val prioridad: StateFlow<Prioridad> = _prioridad.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _errores = MutableStateFlow(ErroresTicket())
    val errores: StateFlow<ErroresTicket> = _errores.asStateFlow()

    fun elegirCategoria(id: Int) {
        _categoriaId.value = id
        _errores.value = _errores.value.copy(categoria = null)
    }

    fun cambiarTitulo(t: String) {
        _titulo.value = t
        _errores.value = _errores.value.copy(titulo = null)
    }

    fun cambiarUbicacion(u: String) {
        _ubicacion.value = u
    }

    fun cambiarPrioridad(p: Prioridad) {
        _prioridad.value = p
    }

    fun cambiarDescripcion(d: String) {
        _descripcion.value = d
        _errores.value = _errores.value.copy(descripcion = null)
    }

    fun enviar(onCreado: () -> Unit) {
        val errores = ErroresTicket(
            categoria = if (_categoriaId.value == null) "Elige una categoría" else null,
            titulo = if (_titulo.value.trim().length < 5) "El título debe tener al menos 5 caracteres" else null,
            descripcion = if (_descripcion.value.trim().length < 10) "La descripción debe tener al menos 10 caracteres" else null
        )
        _errores.value = errores
        if (errores.categoria != null || errores.titulo != null || errores.descripcion != null) return

        viewModelScope.launch {
            repo.crearTicket(
                clienteId = clienteId,
                categoriaId = _categoriaId.value!!,
                titulo = _titulo.value.trim(),
                descripcion = _descripcion.value.trim(),
                ubicacion = _ubicacion.value.trim(),
                prioridad = _prioridad.value
            )
            onCreado()
        }
    }
}