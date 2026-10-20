package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun PerfilScreen(
    sesion: SesionViewModel,
    misTickets: MisTicketsViewModel,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val tickets by misTickets.tickets.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val notificaciones by sesion.notificaciones.collectAsState()

    val cliente = tickets.firstOrNull()?.cliente
    val abiertos = tickets.count { it.ticket.estado == EstadoTicket.ABIERTO.name }
    val resueltos = tickets.count { it.ticket.estado == EstadoTicket.RESUELTO.name }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Perfil", style = MaterialTheme.typography.headlineSmall)

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(cliente?.nombre ?: "Estudiante", style = MaterialTheme.typography.titleMedium)
                    Text(cliente?.correo ?: "", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Contador("Abiertos", abiertos, Modifier.weight(1f))
                Contador("Resueltos", resueltos, Modifier.weight(1f))
                Contador("Totales", tickets.size, Modifier.weight(1f))
            }

            FilaSwitch("Modo oscuro", oscuro, sesion::guardarModoOscuro)
            FilaSwitch("Notificaciones", notificaciones, sesion::guardarNotificaciones)

            OutlinedButton(onClick = onCambiarPerfil, modifier = Modifier.fillMaxWidth()) {
                Text("Cambiar a perfil Soporte TI")
            }
            AyudaButton(text = "Cerrar sesión", onClick = onCerrarSesion)
        }
    }
}

@Composable
private fun Contador(titulo: String, valor: Int, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(valor.toString(), style = MaterialTheme.typography.headlineMedium)
            Text(titulo, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun FilaSwitch(titulo: String, valor: Boolean, onCambiar: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo, style = MaterialTheme.typography.titleSmall)
        Switch(checked = valor, onCheckedChange = onCambiar)
    }
}