package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle

fun iconoCategoria(icono: String): ImageVector = when (icono) {
    "pc" -> Icons.Filled.Computer
    "code" -> Icons.Filled.Code
    "wifi" -> Icons.Filled.Wifi
    "key" -> Icons.Filled.Key
    "printer" -> Icons.Filled.Print
    else -> Icons.Filled.Label
}

@Composable
fun TicketCard(
    detalle: TicketConDetalle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarCliente: Boolean = false
) {
    val t = detalle.ticket
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            Box(
                Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(colorPrioridad(t.prioridad))
            )
            Row(
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    iconoCategoria(detalle.categoria.icono),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("#${t.id} · ${t.titulo}", style = MaterialTheme.typography.titleMedium)
                    Text(
                        if (mostrarCliente) "${detalle.cliente.nombre} · ${t.ubicacion}" else t.ubicacion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        EstadoChip(t.estado)
                        PrioridadChip(t.prioridad)
                    }
                    val slaMillis = when (t.prioridad) {
                        "ALTA" -> 4L * 3600 * 1000
                        "MEDIA" -> 24L * 3600 * 1000
                        else -> 72L * 3600 * 1000
                    }
                    val transcurrido = System.currentTimeMillis() - t.fecha
                    val restante = slaMillis - transcurrido
                    val textoSla = if (t.estado == "CERRADO" || t.estado == "RESUELTO") {
                        "SLA Cumplido"
                    } else if (restante > 0) {
                        val h = restante / (1000 * 60 * 60)
                        "SLA: ${h}h restantes"
                    } else {
                        "SLA Vencido"
                    }
                    Text(
                        textoSla,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (restante < 0 && t.estado != "CERRADO" && t.estado != "RESUELTO") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
