package com.retamozo.clnicasalud.data

import androidx.compose.runtime.mutableStateListOf

object AppointmentRepository {
    val citas = mutableStateListOf(
        Appointment(
            doctorNombre = "Dr. Luis Vega",
            fecha = "Miércoles 15",
            hora = "3:00 pm",
            estado = "Completada"
        )
    )

    fun agendarCita(doctorNombre: String, fecha: String, hora: String) {
        citas.add(0, Appointment(doctorNombre, fecha, hora, "Confirmada"))
    }
}