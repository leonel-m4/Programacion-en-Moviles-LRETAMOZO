package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.tecsupfit.components.StatusBadge
import com.retamozo.tecsupfit.data.Reservation
import com.retamozo.tecsupfit.data.ReservationRepository
import com.retamozo.tecsupfit.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavController? = null) {
    val reservas = ReservationRepository.reservas
    var showDialog by remember { mutableStateOf(false) }
    var reservaSeleccionada by remember { mutableStateOf<Reservation?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis reservas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (navController != null) {
                        IconButton(onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }) {
                            Icon(Icons.Default.Home, contentDescription = "Volver al inicio", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            )
        },
        modifier = Modifier.navigationBarsPadding()
    ) { padding ->
        if (reservas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.EventBusy,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No tienes reservas activas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Explora las clases disponibles en el inicio y reserva tu cupo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reservas, key = { it.id }) { reserva ->
                    ReservationCard(
                        reserva = reserva,
                        onCancelClick = {
                            reservaSeleccionada = reserva
                            showDialog = true
                        }
                    )
                }
            }
        }

        if (showDialog && reservaSeleccionada != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Cancelar reserva") },
                text = { Text("¿Estás seguro de que deseas cancelar tu reserva para '${reservaSeleccionada?.claseNombre}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            reservaSeleccionada?.let { ReservationRepository.cancelarReserva(it) }
                            showDialog = false
                            reservaSeleccionada = null
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Sí, cancelar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Mantener")
                    }
                }
            )
        }
    }
}

@Composable
private fun ReservationCard(reserva: Reservation, onCancelClick: () -> Unit) {
    val isConfirmed = reserva.estado == "Confirmada"
    val barColor = if (isConfirmed) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(6.dp).fillMaxHeight().background(barColor)
            )
            Column(
                modifier = Modifier.weight(1f).padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(reserva.claseNombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    StatusBadge(reserva.estado)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${reserva.fecha} · ${reserva.hora} (${reserva.sala})",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (isConfirmed) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onCancelClick,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cancelar reserva", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
