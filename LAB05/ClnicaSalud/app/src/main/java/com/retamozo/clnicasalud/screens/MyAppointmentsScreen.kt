package com.retamozo.clnicasalud.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.retamozo.clnicasalud.components.StatusBadge
import com.retamozo.clnicasalud.data.Appointment
import com.retamozo.clnicasalud.data.AppointmentRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointmentsScreen(onMenuClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AppointmentRepository.citas) { cita ->
                AppointmentCard(cita)
            }
        }
    }
}

@Composable
private fun AppointmentCard(cita: Appointment) {
    val barColor = if (cita.estado == "Confirmada") {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(barColor))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(cita.doctorNombre, fontWeight = FontWeight.Bold)
                Text(
                    "${cita.fecha}, ${cita.hora}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                StatusBadge(cita.estado)
            }
        }
    }
}