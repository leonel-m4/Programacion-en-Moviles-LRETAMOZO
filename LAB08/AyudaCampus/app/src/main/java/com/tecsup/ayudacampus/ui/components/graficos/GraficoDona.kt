package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GraficoDona(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val total = datos.sumOf { it.valor.toDouble() }.toFloat()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(Modifier.size(140.dp)) {
                val grosor = 28.dp.toPx()
                val margen = grosor / 2
                val tam = Size(size.width - grosor, size.height - grosor)
                val estilo = Stroke(width = grosor)
                if (total == 0f) {
                    drawArc(
                        color = Color.LightGray,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = Offset(margen, margen),
                        size = tam,
                        style = estilo
                    )
                } else {
                    var inicio = -90f
                    datos.forEach {
                        val barrido = it.valor / total * 360f
                        drawArc(
                            color = it.color,
                            startAngle = inicio,
                            sweepAngle = barrido,
                            useCenter = false,
                            topLeft = Offset(margen, margen),
                            size = tam,
                            style = estilo
                        )
                        inicio += barrido
                    }
                }
            }
            Text(total.toInt().toString(), style = MaterialTheme.typography.headlineSmall)
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            datos.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(10.dp)
                            .background(it.color, CircleShape)
                    )
                    Text(
                        "  ${it.etiqueta}: ${it.valor.toInt()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}