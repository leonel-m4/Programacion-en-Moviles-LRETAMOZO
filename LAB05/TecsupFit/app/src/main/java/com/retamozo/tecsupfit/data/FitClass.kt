package com.retamozo.tecsupfit.data

data class FitClass(
    val id: Int,
    val nombre: String,
    val hora: String,
    val sala: String,
    val duracionMin: Int,
    val descripcion: String,
    var cuposDisponibles: Int,
    val cuposTotales: Int,
    val instructorNombre: String,
    val instructorFotoUrl: String,
    val categoria: String
)
