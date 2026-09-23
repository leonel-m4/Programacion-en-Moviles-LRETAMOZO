package com.retamozo.clnicasalud.data

data class MedicalRecord(
    val id: Int,
    val titulo: String,
    val doctorNombre: String,
    val especialidad: String,
    val fecha: String,
    val diagnostico: String,
    val receta: String
)
