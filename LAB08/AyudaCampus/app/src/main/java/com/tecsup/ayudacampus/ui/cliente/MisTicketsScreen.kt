package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel

@Composable
fun MisTicketsScreen(
    vm: MisTicketsViewModel,
    onTicket: (Int) -> Unit,
    onNuevo: () -> Unit,
    recibirTickets: Boolean = true
) {
    val tickets by vm.tickets.collectAsState()
    val estado by vm.estado.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val abiertos by vm.abiertos.collectAsState()

    val filtros = listOf(
        "Todos" to null,
        "Abiertos" to EstadoTicket.ABIERTO,
        "En proceso" to EstadoTicket.EN_PROCESO,
        "Resueltos" to EstadoTicket.RESUELTO
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { if (recibirTickets) onNuevo() }) {
                Icon(Icons.Filled.Add, contentDescription = "Nuevo ticket")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Hola", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Tienes $abiertos tickets abiertos",
                style = MaterialTheme.typography.bodyMedium
            )

            if (!recibirTickets) {
                Text("Mesa de ayuda cerrada", color = MaterialTheme.colorScheme.error)
            }

            AyudaTextField(
                value = busqueda,
                onValueChange = vm::buscar,
                label = "Buscar por título o número"
            )

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
                    Text("No tienes tickets aquí")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(tickets, key = { it.ticket.id }) { d ->
                        TicketCard(detalle = d, onClick = { onTicket(d.ticket.id) })
                    }
                }
            }
        }
    }
}