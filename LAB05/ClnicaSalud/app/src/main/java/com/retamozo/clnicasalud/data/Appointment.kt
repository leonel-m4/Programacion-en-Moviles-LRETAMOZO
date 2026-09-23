package com.retamozo.clnicasalud.data

data class Appointment(
    val doctorNombre: String,
    val fecha: String,
    val hora: String,
    val estado: String
)