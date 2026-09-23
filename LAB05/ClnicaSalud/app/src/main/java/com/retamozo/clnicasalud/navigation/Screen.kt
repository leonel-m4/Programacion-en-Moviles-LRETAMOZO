package com.retamozo.clnicasalud.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MyAppointments : Screen("myAppointments")
    object MedicalHistory : Screen("medicalHistory")
    object Profile : Screen("profile")

    object DoctorProfile : Screen("doctorProfile/{doctorId}") {
        fun createRoute(doctorId: Int) = "doctorProfile/$doctorId"
    }

    object BookAppointment : Screen("bookAppointment/{doctorId}") {
        fun createRoute(doctorId: Int) = "bookAppointment/$doctorId"
    }

    object Confirmation : Screen("confirmation/{doctorId}/{dateIndex}/{timeIndex}") {
        fun createRoute(doctorId: Int, dateIndex: Int, timeIndex: Int) =
            "confirmation/$doctorId/$dateIndex/$timeIndex"
    }
}