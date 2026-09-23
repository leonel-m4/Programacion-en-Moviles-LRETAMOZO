package com.retamozo.tecsupfit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.retamozo.tecsupfit.navigation.Screen

private data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val items = listOf(
    BottomNavItem(Screen.Home.route, "Inicio", Icons.Default.Home),
    BottomNavItem(Screen.Reservations.route, "Reservas", Icons.Default.CalendarMonth),
    BottomNavItem(Screen.Routines.route, "Rutinas", Icons.Default.FitnessCenter),
    BottomNavItem(Screen.Profile.route, "Perfil", Icons.Default.Person)
)

@Composable
fun BottomNavigationBar(currentRoute: String?, navController: NavController) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}