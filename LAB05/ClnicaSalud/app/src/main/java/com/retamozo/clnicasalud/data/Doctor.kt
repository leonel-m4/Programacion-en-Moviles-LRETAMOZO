package com.retamozo.clnicasalud.data

data class Doctor(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val numResenas: Int,
    val experienciaAnios: Int,
    val descripcion: String
)