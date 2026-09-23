package com.retamozo.clnicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.clnicasalud.components.SelectableChip
import com.retamozo.clnicasalud.data.dateOptions
import com.retamozo.clnicasalud.data.timeOptions
import com.retamozo.clnicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(navController: NavController, doctorId: Int) {
    var selectedDateIndex by remember { mutableStateOf(1) }
    var selectedTimeIndex by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.Confirmation.createRoute(doctorId, selectedDateIndex, selectedTimeIndex)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Confirmar cita", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Selecciona fecha", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                dateOptions.forEachIndexed { index, option ->
                    SelectableChip(selected = selectedDateIndex == index, onClick = { selectedDateIndex = index }) {
                        Text(option.diaCorto, style = MaterialTheme.typography.bodySmall)
                        Text(option.diaNumero.toString(), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text("Selecciona hora", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                timeOptions.forEachIndexed { index, option ->
                    SelectableChip(selected = selectedTimeIndex == index, onClick = { selectedTimeIndex = index }) {
                        Text(option.chipLabel, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
