package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.tecsupfit.data.ClassRepository
import com.retamozo.tecsupfit.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(navController: NavController, classId: Int) {
    val fitClass = ClassRepository.getById(classId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmación", fontWeight = FontWeight.Bold) }
            )
        },
        modifier = Modifier.navigationBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(80.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("¡Cupo reservado con éxito!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(fitClass.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Hoy, ${fitClass.hora} · ${fitClass.sala}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Volver al inicio", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.Reservations.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver mis reservas", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
