package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.retamozo.tecsupfit.components.StatusBadge
import com.retamozo.tecsupfit.data.Reservation
import com.retamozo.tecsupfit.data.ReservationRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis reservas") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ReservationRepository.reservas) { reserva ->
                ReservationCard(reserva)
            }
        }
    }
}

@Composable
private fun ReservationCard(reserva: Reservation) {
    val barColor = if (reserva.estado == "Confirmada") {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(barColor))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(reserva.claseNombre, fontWeight = FontWeight.Bold)
                Text(
                    "${reserva.fecha}, ${reserva.hora}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                StatusBadge(reserva.estado)
            }
        }
    }
}