package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TicketDetalleViewModel(
    private val repo: AyudaRepository,
    private val ticketId: Int
) : ViewModel() {

    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun comentar(texto: String) {
        val d = ticket.value ?: return
        if (texto.isBlank() || d.ticket.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch {
            repo.agregarComentario(ticketId, d.cliente, texto.trim())
        }
    }

    fun cerrar(calificacion: Int, comentarioFinal: String = "") {
        val d = ticket.value ?: return
        if (d.ticket.estado != EstadoTicket.RESUELTO.name) return
        if (calificacion !in 1..5) return
        viewModelScope.launch {
            if (comentarioFinal.isNotBlank()) {
                repo.agregarComentario(ticketId, d.cliente, comentarioFinal.trim())
            }
            repo.cerrarConCalificacion(ticketId, calificacion)
        }
    }

    fun reabrir() {
        val d = ticket.value ?: return
        if (d.ticket.estado != EstadoTicket.RESUELTO.name) return
        viewModelScope.launch { repo.reabrir(ticketId) }
    }
}