package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(repo: AyudaRepository) : ViewModel() {

    private val todos = repo.observarTodosLosTickets()

    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estado: StateFlow<EstadoTicket?> = _estado.asStateFlow()

    private val _texto = MutableStateFlow("")
    val texto: StateFlow<String> = _texto.asStateFlow()

    private val _prioridad = MutableStateFlow<String?>(null)
    val prioridad: StateFlow<String?> = _prioridad.asStateFlow()

    private val _categoriaId = MutableStateFlow<Int?>(null)
    val categoriaId: StateFlow<Int?> = _categoriaId.asStateFlow()

    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _estado, _texto, _prioridad, _categoriaId) { lista, estado, texto, prioridad, catId ->
            lista.filter { d ->
                val matchEstado = estado == null || d.ticket.estado == estado.name
                val matchPrioridad = prioridad == null || d.ticket.prioridad == prioridad
                val matchCat = catId == null || d.ticket.categoriaId == catId
                val matchTexto = texto.isBlank() ||
                        d.ticket.titulo.contains(texto, ignoreCase = true) ||
                        d.ticket.descripcion.contains(texto, ignoreCase = true) ||
                        d.cliente.nombre.contains(texto, ignoreCase = true)
                matchEstado && matchPrioridad && matchCat && matchTexto
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seleccionarEstado(e: EstadoTicket?) {
        _estado.value = e
    }

    fun buscarTexto(t: String) {
        _texto.value = t
    }

    fun seleccionarPrioridad(p: String?) {
        _prioridad.value = p
    }

    fun seleccionarCategoria(c: Int?) {
        _categoriaId.value = c
    }
}
