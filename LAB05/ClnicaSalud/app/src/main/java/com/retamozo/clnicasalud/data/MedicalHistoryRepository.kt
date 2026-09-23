package com.retamozo.clnicasalud.data

object MedicalHistoryRepository {
    val registros = listOf(
        MedicalRecord(
            id = 1,
            titulo = "Control Cardiológico Anual",
            doctorNombre = "Dra. Ana Torres",
            especialidad = "Cardiología",
            fecha = "10 de Agosto, 2026",
            diagnostico = "Presión arterial estable, ritmo sinusal normal.",
            receta = "Continuar con hábitos saludables y control en 6 meses."
        ),
        MedicalRecord(
            id = 2,
            titulo = "Consulta Pediátrica General",
            doctorNombre = "Dr. Luis Vega",
            especialidad = "Pediatría",
            fecha = "22 de Julio, 2026",
            diagnostico = "Desarrollo psicomotor adecuado para la edad.",
            receta = "Multivitamínico en gotas según pauta."
        )
    )
}
