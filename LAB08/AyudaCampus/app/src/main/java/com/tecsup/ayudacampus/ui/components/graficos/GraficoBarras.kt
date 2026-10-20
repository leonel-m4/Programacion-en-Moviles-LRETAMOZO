package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GraficoBarras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        datos.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    it.etiqueta,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(96.dp)
                )
                Box(Modifier.weight(1f)) {
                    Box(
                        Modifier
                            .fillMaxWidth(it.valor / maximo)
                            .height(16.dp)
                            .background(it.color, RoundedCornerShape(4.dp))
                    )
                }
                Text(
                    "  ${it.valor.toInt()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}