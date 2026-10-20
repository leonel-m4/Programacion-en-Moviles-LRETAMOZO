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
import androidx.compose.material3.Surface
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
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(vm: BandejaViewModel, onTicket: (Int) -> Unit) {
    val tickets by vm.tickets.collectAsState()
    val estado by vm.estado.collectAsState()
    val texto by vm.texto.collectAsState()
    val prioridad by vm.prioridad.collectAsState()

    val filtrosEstado = listOf(
        "Todos" to null,
        "Abiertos" to EstadoTicket.ABIERTO,
        "En proceso" to EstadoTicket.EN_PROCESO,
        "Resueltos" to EstadoTicket.RESUELTO
    )

    val filtrosPrioridad = listOf(
        "Todas" to null,
        "Alta" to "ALTA",
        "Media" to "MEDIA",
        "Baja" to "BAJA"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bandeja de tickets", style = MaterialTheme.typography.headlineSmall)

            AyudaTextField(
                value = texto,
                onValueChange = { vm.buscarTexto(it) },
                label = "Buscar por título, descripción o cliente"
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filtrosEstado.forEach { (nombre, valor) ->
                    FilterChip(
                        selected = estado == valor,
                        onClick = { vm.seleccionarEstado(valor) },
                        label = { Text(nombre) }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Prioridad:", modifier = Modifier.align(Alignment.CenterVertically), style = MaterialTheme.typography.bodySmall)
                filtrosPrioridad.forEach { (nombre, valor) ->
                    FilterChip(
                        selected = prioridad == valor,
                        onClick = { vm.seleccionarPrioridad(valor) },
                        label = { Text(nombre) }
                    )
                }
            }

            if (tickets.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay tickets que coincidan con los filtros")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
}
