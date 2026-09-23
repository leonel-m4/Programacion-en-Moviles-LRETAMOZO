package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retamozo.tecsupfit.components.ClassCard
import com.retamozo.tecsupfit.data.ClassRepository
import com.retamozo.tecsupfit.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var filtroSeleccionado by remember { mutableStateOf("Hoy") }
    val filtros = listOf("Hoy", "Mañana", "Esta semana")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("TECSUP Fit", fontWeight = FontWeight.Bold)
                        Text("Bienvenido, Diego", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            )
        },
        modifier = Modifier.navigationBarsPadding()
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filtros) { filtro ->
                    FilterChip(
                        selected = filtroSeleccionado == filtro,
                        onClick = { filtroSeleccionado = filtro },
                        label = { Text(filtro) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Clases disponibles",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ClassRepository.clases) { fitClass ->
                    ClassCard(
                        fitClass = fitClass,
                        onClick = { navController.navigate(Screen.ClassDetail.createRoute(fitClass.id)) }
                    )
                }
            }
        }
    }
}
