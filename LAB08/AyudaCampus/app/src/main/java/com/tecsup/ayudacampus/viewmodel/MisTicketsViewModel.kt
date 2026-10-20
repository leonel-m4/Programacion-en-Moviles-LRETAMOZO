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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MisTicketsViewModel(
    repo: AyudaRepository,
    clienteId: Int
) : ViewModel() {

    private val todos = repo.observarTicketsCliente(clienteId)

    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estado: StateFlow<EstadoTicket?> = _estado.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _estado, _busqueda) { lista, estado, texto ->
            val q = texto.trim().removePrefix("#")
            lista
                .filter { estado == null || it.ticket.estado == estado.name }
                .filter {
                    q.isEmpty() ||
                            it.ticket.titulo.contains(q, ignoreCase = true) ||
                            it.ticket.id.toString() == q
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val abiertos: StateFlow<Int> = todos
        .map { lista -> lista.count { it.ticket.estado == EstadoTicket.ABIERTO.name } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun seleccionarEstado(e: EstadoTicket?) {
        _estado.value = e
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }
}