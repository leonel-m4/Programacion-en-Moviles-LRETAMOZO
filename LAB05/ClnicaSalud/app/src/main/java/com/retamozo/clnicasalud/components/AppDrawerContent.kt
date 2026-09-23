package com.retamozo.clnicasalud.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.retamozo.clnicasalud.data.Doctor
import com.retamozo.clnicasalud.navigation.Screen

@Composable
fun AppDrawerContent(currentRoute: String?, onNavigate: (String) -> Unit) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("JP", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Juan Pérez", fontWeight = FontWeight.Bold)
                    Text(
                        "Paciente",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            DrawerMenuItem("Inicio", Screen.Home.route, currentRoute, onNavigate)
            DrawerMenuItem("Mis citas", Screen.MyAppointments.route, currentRoute, onNavigate)
            DrawerMenuItem("Historial médico", Screen.MedicalHistory.route, currentRoute, onNavigate)
            DrawerMenuItem("Perfil", Screen.Profile.route, currentRoute, onNavigate)
        }
    }
}

@Composable
private fun DrawerMenuItem(
    label: String,
    route: String,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val selected = currentRoute == route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
            .clickable { onNavigate(route) }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}