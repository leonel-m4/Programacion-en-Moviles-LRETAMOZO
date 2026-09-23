package com.retamozo.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Rutinas") }) }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding).fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Aún no tienes rutinas asignadas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}