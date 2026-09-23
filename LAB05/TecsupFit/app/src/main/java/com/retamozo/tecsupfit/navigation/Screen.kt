package com.retamozo.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Reservations : Screen("reservations")
    object Routines : Screen("routines")
    object Profile : Screen("profile")

    object ClassDetail : Screen("classDetail/{classId}") {
        fun createRoute(classId: Int) = "classDetail/$classId"
    }

    object Confirmation : Screen("confirmation/{classId}") {
        fun createRoute(classId: Int) = "confirmation/$classId"
    }
}

val bottomBarRoutes = listOf(
    Screen.Home.route,
    Screen.Reservations.route,
    Screen.Routines.route,
    Screen.Profile.route
)