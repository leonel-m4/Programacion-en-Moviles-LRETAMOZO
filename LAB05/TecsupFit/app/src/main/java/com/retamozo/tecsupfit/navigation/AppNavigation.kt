package com.retamozo.tecsupfit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.retamozo.tecsupfit.components.BottomNavigationBar
import com.retamozo.tecsupfit.screens.ClassDetailScreen
import com.retamozo.tecsupfit.screens.ConfirmationScreen
import com.retamozo.tecsupfit.screens.HomeScreen
import com.retamozo.tecsupfit.screens.ProfileScreen
import com.retamozo.tecsupfit.screens.ReservationsScreen
import com.retamozo.tecsupfit.screens.RoutinesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(currentRoute = currentRoute, navController = navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Reservations.route) {
                ReservationsScreen(navController = navController)
            }
            composable(Screen.Routines.route) {
                RoutinesScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.ClassDetail.route,
                arguments = listOf(navArgument("classId") { type = NavType.IntType })
            ) { backStackEntry ->
                val classId = backStackEntry.arguments?.getInt("classId") ?: 0
                ClassDetailScreen(navController = navController, classId = classId)
            }
            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(navArgument("classId") { type = NavType.IntType })
            ) { backStackEntry ->
                val classId = backStackEntry.arguments?.getInt("classId") ?: 0
                ConfirmationScreen(navController = navController, classId = classId)
            }
        }
    }
}
