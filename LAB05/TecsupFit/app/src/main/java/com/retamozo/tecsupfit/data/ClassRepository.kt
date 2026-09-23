package com.retamozo.tecsupfit.data

object ClassRepository {
    val clases = listOf(
        FitClass(
            id = 1,
            nombre = "Yoga funcional",
            hora = "7:00 am",
            sala = "Sala 2",
            duracionMin = 50,
            descripcion = "Trabajo de movilidad, respiración y fuerza funcional para todo nivel.",
            cuposDisponibles = 6,
            cuposTotales = 15
        ),
        FitClass(
            id = 2,
            nombre = "Cross Training",
            hora = "6:00 pm",
            sala = "Sala 1",
            duracionMin = 45,
            descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
            cuposDisponibles = 8,
            cuposTotales = 12
        ),
        FitClass(
            id = 3,
            nombre = "Spinning",
            hora = "7:30 pm",
            sala = "Sala 3",
            duracionMin = 40,
            descripcion = "Rutina cardiovascular sobre bicicleta estática, ritmo guiado por instructor.",
            cuposDisponibles = 10,
            cuposTotales = 20
        )
    )

    fun getById(id: Int): FitClass = clases.first { it.id == id }
}