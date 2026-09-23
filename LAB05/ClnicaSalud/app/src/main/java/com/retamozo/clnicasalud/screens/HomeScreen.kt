package com.retamozo.clnicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.clnicasalud.components.DoctorCard
import com.retamozo.clnicasalud.data.DoctorRepository
import com.retamozo.clnicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, onMenuClick: () -> Unit) {
    var selectedEspecialidad by remember { mutableStateOf("Todas") }
    val especialidades = DoctorRepository.especialidades()
    val doctoresFiltrados = if (selectedEspecialidad == "Todas") {
        DoctorRepository.doctores
    } else {
        DoctorRepository.doctores.filter { it.especialidad == selectedEspecialidad }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Clínica Salud+", fontWeight = FontWeight.Bold)
                        Text("Hola, Juan", style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(especialidades) { especialidad ->
                    FilterChip(
                        selected = selectedEspecialidad == especialidad,
                        onClick = { selectedEspecialidad = especialidad },
                        label = { Text(especialidad) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Médicos disponibles",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(doctoresFiltrados) { doctor ->
                    DoctorCard(
                        doctor = doctor,
                        onClick = { navController.navigate(Screen.DoctorProfile.createRoute(doctor.id)) }
                    )
                }
            }
        }
    }
}