package com.retamozo.portalacademico.model

data class Alumno(
    val id: Int = 0,
    val nombre: String,
    val carrera: String,
    val fotoUrl: String,
    val idEstudiante: String = "",
    val correo: String = "",
    val facultad: String = "",
    val biografia: String = ""
)

object StudentData {
    val sampleStudents = listOf(
        Alumno(
            id = 1,
            nombre = "Leonel Retamozo",
            carrera = "Ingeniería de Sistemas",
            fotoUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
            idEstudiante = "2024-0001",
            correo = "leonel.retamozo@tecsup.edu.pe",
            facultad = "Ingeniería y Tecnología",
            biografia = "Estudiante destacado con interés en desarrollo Android."
        ),
        Alumno(
            id = 2,
            nombre = "María García",
            carrera = "Arquitectura",
            fotoUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
            idEstudiante = "2024-0002",
            correo = "maria.garcia@example.com",
            facultad = "Arquitectura y Diseño",
            biografia = "Apasionada por la arquitectura sostenible y el diseño urbano."
        ),
        Alumno(
            id = 3,
            nombre = "Carlos Perez",
            carrera = "Medicina",
            fotoUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400",
            idEstudiante = "2024-0003",
            correo = "carlos.perez@example.com",
            facultad = "Ciencias de la Salud",
            biografia = "Interesado en la investigación médica y la telemedicina."
        ),
        Alumno(
            id = 4,
            nombre = "Ana Lopez",
            carrera = "Derecho",
            fotoUrl = "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=400",
            idEstudiante = "2024-0004",
            correo = "ana.lopez@example.com",
            facultad = "Derecho y Ciencias Políticas",
            biografia = "Especializándose en derecho digital y propiedad intelectual."
        ),
        Alumno(
            id = 5,
            nombre = "Luis Ramirez",
            carrera = "Administración",
            fotoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
            idEstudiante = "2024-0005",
            correo = "luis.ramirez@example.com",
            facultad = "Empresa y Negocios",
            biografia = "Enfocado en emprendimiento e innovación de negocios tecnológicos."
        )
    )

    fun getStudentById(id: Int): Alumno {
        return sampleStudents.find { it.id == id } ?: sampleStudents.first()
    }
}
