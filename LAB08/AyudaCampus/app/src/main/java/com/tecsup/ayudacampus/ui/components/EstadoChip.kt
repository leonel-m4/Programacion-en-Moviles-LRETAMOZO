package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket

fun colorEstado(estado: String): Color = when (estado) {
    EstadoTicket.ABIERTO.name -> Color(0xFFD97706)
    EstadoTicket.EN_PROCESO.name -> Color(0xFF2563EB)
    EstadoTicket.RESUELTO.name -> Color(0xFF16A34A)
    else -> Color(0xFF6B7280)
}

@Composable
fun EstadoChip(estado: String, modifier: Modifier = Modifier) {
    val c = colorEstado(estado)
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = c.copy(alpha = 0.15f)
    ) {
        Text(
            text = estado.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
            color = c,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}