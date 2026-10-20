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
import com.tecsup.ayudacampus.model.Prioridad

fun colorPrioridad(prioridad: String): Color = when (prioridad) {
    Prioridad.ALTA.name -> Color(0xFFDC2626)
    Prioridad.MEDIA.name -> Color(0xFFB45309)
    else -> Color(0xFF6B7280)
}

@Composable
fun PrioridadChip(prioridad: String, modifier: Modifier = Modifier) {
    val c = colorPrioridad(prioridad)
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = c.copy(alpha = 0.15f)
    ) {
        Text(
            text = prioridad.lowercase().replaceFirstChar { it.uppercase() },
            color = c,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}