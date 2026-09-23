package com.retamozo.tecsupfit.data

import java.util.UUID

data class Reservation(
    val id: String = UUID.randomUUID().toString(),
    val claseNombre: String,
    val fecha: String,
    val hora: String,
    val sala: String,
    val estado: String
)
