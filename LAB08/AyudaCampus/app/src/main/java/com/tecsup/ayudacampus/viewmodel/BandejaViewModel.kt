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

    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _estado) { lista, estado ->
            if (estado == null) lista else lista.filter { it.ticket.estado == estado.name }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seleccionarEstado(e: EstadoTicket?) {
        _estado.value = e
    }
}