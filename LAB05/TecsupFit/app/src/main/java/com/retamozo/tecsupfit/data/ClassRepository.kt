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
            cuposTotales = 15,
            instructorNombre = "Lucía Fernández",
            instructorFotoUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400&auto=format&fit=crop&q=80",
            categoria = "Bienestar"
        ),
        FitClass(
            id = 2,
            nombre = "Cross Training",
            hora = "6:00 pm",
            sala = "Sala 1",
            duracionMin = 45,
            descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
            cuposDisponibles = 8,
            cuposTotales = 12,
            instructorNombre = "Carlos Mendoza",
            instructorFotoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&auto=format&fit=crop&q=80",
            categoria = "Fuerza"
        ),
        FitClass(
            id = 3,
            nombre = "Spinning Pro",
            hora = "7:30 pm",
            sala = "Sala 3",
            duracionMin = 40,
            descripcion = "Rutina cardiovascular sobre bicicleta estática, ritmo guiado por instructor.",
            cuposDisponibles = 10,
            cuposTotales = 20,
            instructorNombre = "Marco Silva",
            instructorFotoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&auto=format&fit=crop&q=80",
            categoria = "Cardio"
        ),
        FitClass(
            id = 4,
            nombre = "HIIT Quema Grasa",
            hora = "8:00 am",
            sala = "Sala 1",
            duracionMin = 35,
            descripcion = "Intervalos de alta intensidad para acelerar el metabolismo y quemar calorías.",
            cuposDisponibles = 4,
            cuposTotales = 15,
            instructorNombre = "Sofía Vargas",
            instructorFotoUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&auto=format&fit=crop&q=80",
            categoria = "Cardio"
        )
    )

    fun getById(id: Int): FitClass = clases.first { it.id == id }

    fun decrementarCupo(id: Int) {
        val clase = clases.find { it.id == id }
        if (clase != null && clase.cuposDisponibles > 0) {
            clase.cuposDisponibles--
        }
    }
}
