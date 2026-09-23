package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.tecsupfit.data.ClassRepository
import com.retamozo.tecsupfit.data.ReservationRepository
import com.retamozo.tecsupfit.navigation.Screen

@Composable
fun ConfirmationScreen(navController: NavController, classId: Int) {
    val fitClass = ClassRepository.getById(classId)

    LaunchedEffect(Unit) {
        ReservationRepository.reservar(fitClass.nombre, fitClass.hora, fitClass.sala)
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFD7F5E3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF1E7A46), modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("¡Cupo reservado!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(fitClass.nombre, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Hoy, ${fitClass.hora} · ${fitClass.sala}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.Reservations.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            ) {
                Text("Ver mis reservas")
            }
        }
    }
}