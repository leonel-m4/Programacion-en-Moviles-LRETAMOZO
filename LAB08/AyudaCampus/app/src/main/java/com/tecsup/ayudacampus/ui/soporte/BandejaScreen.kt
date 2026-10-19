package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(vm: BandejaViewModel, onTicket: (Int) -> Unit) {
    val tickets by vm.tickets.collectAsState()
    val estado by vm.estado.collectAsState()

    val filtros = listOf(
        "Todos" to null,
        "Abiertos" to EstadoTicket.ABIERTO,
        "En proceso" to EstadoTicket.EN_PROCESO,
        "Resueltos" to EstadoTicket.RESUELTO
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Bandeja de tickets", style = MaterialTheme.typography.headlineSmall)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filtros.forEach { (nombre, valor) ->
                FilterChip(
                    selected = estado == valor,
                    onClick = { vm.seleccionarEstado(valor) },
                    label = { Text(nombre) }
                )
            }
        }

        if (tickets.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay tickets aquí")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tickets, key = { it.ticket.id }) { d ->
                    TicketCard(
                        detalle = d,
                        mostrarCliente = true,
                        onClick = { onTicket(d.ticket.id) }
                    )
                }
            }
        }
    }
}