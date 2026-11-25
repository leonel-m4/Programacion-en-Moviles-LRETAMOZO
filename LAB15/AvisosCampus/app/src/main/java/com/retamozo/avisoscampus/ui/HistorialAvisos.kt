package com.retamozo.avisoscampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.retamozo.avisoscampus.model.Aviso
import com.retamozo.avisoscampus.model.Origen

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Historial",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            val recientes = avisos.take(20)
            if (recientes.isEmpty()) {
                Text("Sin avisos todavía")
            } else {
                recientes.forEach { aviso ->
                    FilaAviso(aviso)
                }
            }
        }
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EtiquetaOrigen(aviso.origen)
        Column(modifier = Modifier.weight(1f)) {
            Text(aviso.titulo, fontWeight = FontWeight.Bold)
            Text(aviso.texto, style = MaterialTheme.typography.bodySmall)
        }
        Text(aviso.hora, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun EtiquetaOrigen(origen: Origen) {
    val color = when (origen) {
        Origen.BOTON -> Color(0xFF1565C0)
        Origen.RECORDATORIO -> Color(0xFFF9A825)
        Origen.MODO_AVION -> Color(0xFF7E57C2)
        Origen.CARGADOR -> Color(0xFF2E7D32)
    }
    Surface(color = color, shape = RoundedCornerShape(50)) {
        Text(
            text = origen.texto,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}
