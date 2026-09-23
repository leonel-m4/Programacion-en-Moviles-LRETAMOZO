package com.retamozo.clnicasalud.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.retamozo.clnicasalud.components.AppDrawerContent
import com.retamozo.clnicasalud.screens.BookAppointmentScreen
import com.retamozo.clnicasalud.screens.ConfirmationScreen
import com.retamozo.clnicasalud.screens.DoctorProfileScreen
import com.retamozo.clnicasalud.screens.HomeScreen
import com.retamozo.clnicasalud.screens.MedicalHistoryScreen
import com.retamozo.clnicasalud.screens.MyAppointmentsScreen
import com.retamozo.clnicasalud.screens.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {

            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }

            composable(Screen.MyAppointments.route) {
                MyAppointmentsScreen(onMenuClick = { scope.launch { drawerState.open() } })
            }

            composable(Screen.MedicalHistory.route) {
                MedicalHistoryScreen(onMenuClick = { scope.launch { drawerState.open() } })
            }

            composable(Screen.Profile.route) {
                ProfileScreen(onMenuClick = { scope.launch { drawerState.open() } })
            }

            composable(
                route = Screen.DoctorProfile.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                DoctorProfileScreen(navController = navController, doctorId = doctorId)
            }

            composable(
                route = Screen.BookAppointment.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                BookAppointmentScreen(navController = navController, doctorId = doctorId)
            }

            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.IntType },
                    navArgument("dateIndex") { type = NavType.IntType },
                    navArgument("timeIndex") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
                val dateIndex = backStackEntry.arguments?.getInt("dateIndex") ?: 0
                val timeIndex = backStackEntry.arguments?.getInt("timeIndex") ?: 0
                ConfirmationScreen(
                    navController = navController,
                    doctorId = doctorId,
                    dateIndex = dateIndex,
                    timeIndex = timeIndex
                )
            }
        }
    }
}