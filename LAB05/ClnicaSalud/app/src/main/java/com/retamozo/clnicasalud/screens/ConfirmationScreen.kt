package com.retamozo.clnicasalud.screens

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
import com.retamozo.clnicasalud.data.AppointmentRepository
import com.retamozo.clnicasalud.data.DoctorRepository
import com.retamozo.clnicasalud.data.dateOptions
import com.retamozo.clnicasalud.data.timeOptions
import com.retamozo.clnicasalud.navigation.Screen

@Composable
fun ConfirmationScreen(
    navController: NavController,
    doctorId: Int,
    dateIndex: Int,
    timeIndex: Int
) {
    val doctor = DoctorRepository.getById(doctorId)
    val dateOption = dateOptions[dateIndex]
    val timeOption = timeOptions[timeIndex]
    val fechaTexto = "${dateOption.diaCompleto} ${dateOption.diaNumero}"

    LaunchedEffect(Unit) {
        AppointmentRepository.agendarCita(
            doctorNombre = doctor.nombre,
            fecha = fechaTexto,
            hora = timeOption.displayLabel
        )
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
            Text("¡Cita agendada!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(doctor.nombre, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("$fechaTexto, ${timeOption.displayLabel}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.MyAppointments.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            ) {
                Text("Ver mis citas")
            }
        }
    }
}