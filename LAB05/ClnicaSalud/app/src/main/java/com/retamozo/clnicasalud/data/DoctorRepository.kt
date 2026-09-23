package com.retamozo.clnicasalud.data

object DoctorRepository {
    val doctores = listOf(
        Doctor(
            id = 1,
            nombre = "Dra. Ana Torres",
            especialidad = "Cardiología",
            calificacion = 4.9,
            numResenas = 128,
            experienciaAnios = 12,
            descripcion = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
        ),
        Doctor(
            id = 2,
            nombre = "Dr. Luis Vega",
            especialidad = "Pediatría",
            calificacion = 4.7,
            numResenas = 94,
            experienciaAnios = 8,
            descripcion = "Especialista en pediatría general y control de niño sano."
        ),
        Doctor(
            id = 3,
            nombre = "Dra. Rosa Díaz",
            especialidad = "Dermatología",
            calificacion = 4.8,
            numResenas = 76,
            experienciaAnios = 10,
            descripcion = "Especialista en dermatología clínica y estética."
        )
    )

    fun getById(id: Int): Doctor = doctores.first { it.id == id }

    fun especialidades(): List<String> =
        listOf("Todas") + doctores.map { it.especialidad }.distinct()
}