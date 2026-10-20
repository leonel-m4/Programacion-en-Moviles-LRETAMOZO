package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtenderViewModel(
    private val repo: AyudaRepository,
    private val ticketId: Int,
    private val autor: Usuario
) : ViewModel() {

    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estado: StateFlow<EstadoTicket?> = _estado.asStateFlow()

    private val _prioridad = MutableStateFlow<Prioridad?>(null)
    val prioridad: StateFlow<Prioridad?> = _prioridad.asStateFlow()

    init {
        viewModelScope.launch {
            val d = repo.observarTicket(ticketId).filterNotNull().first()
            _estado.value = EstadoTicket.valueOf(d.ticket.estado)
            _prioridad.value = Prioridad.valueOf(d.ticket.prioridad)
        }
    }

    fun cambiarEstado(e: EstadoTicket) {
        val d = ticket.value ?: return
        if (d.ticket.estado == EstadoTicket.CERRADO.name) return
        if (e == EstadoTicket.CERRADO) return
        _estado.value = e
    }

    fun cambiarPrioridad(p: Prioridad) {
        _prioridad.value = p
    }

    fun responder(texto: String) {
        val d = ticket.value ?: return
        if (texto.isBlank() || d.ticket.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch { repo.agregarComentario(ticketId, autor, texto.trim()) }
    }

    fun guardar(onListo: () -> Unit) {
        val d = ticket.value ?: return
        val e = _estado.value
        val p = _prioridad.value
        viewModelScope.launch {
            if (e != null && d.ticket.estado != EstadoTicket.CERRADO.name && e.name != d.ticket.estado) {
                repo.cambiarEstado(ticketId, e)
            }
            if (p != null && p.name != d.ticket.prioridad) {
                repo.cambiarPrioridad(ticketId, p)
            }
            onListo()
        }
    }
}