package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(
    sesion: SesionViewModel,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val oscuro by sesion.modoOscuro.collectAsState()
    val notificaciones by sesion.notificaciones.collectAsState()
    val recibir by sesion.recibirTickets.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ajustes", style = MaterialTheme.typography.headlineSmall)

        Fila("Modo oscuro", oscuro, sesion::guardarModoOscuro)
        Fila("Notificaciones", notificaciones, sesion::guardarNotificaciones)
        Fila("Recibir tickets", recibir, sesion::guardarRecibirTickets)

        OutlinedButton(onClick = onCambiarPerfil, modifier = Modifier.fillMaxWidth()) {
            Text("Cambiar a perfil Estudiante")
        }
        AyudaButton(text = "Cerrar sesión", onClick = onCerrarSesion)
    }
}

@Composable
private fun Fila(titulo: String, valor: Boolean, onCambiar: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo, style = MaterialTheme.typography.titleSmall)
        Switch(checked = valor, onCheckedChange = onCambiar)
    }
}