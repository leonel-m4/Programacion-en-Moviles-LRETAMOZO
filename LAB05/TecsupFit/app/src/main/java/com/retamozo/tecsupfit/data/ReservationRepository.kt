package com.retamozo.tecsupfit.data

import androidx.compose.runtime.mutableStateListOf

object ReservationRepository {
    val reservas = mutableStateListOf(
        Reservation(
            claseNombre = "Yoga funcional",
            fecha = "Ayer",
            hora = "7:00 am",
            sala = "Sala 2",
            estado = "Completada"
        )
    )

    fun reservar(claseNombre: String, hora: String, sala: String) {
        reservas.add(0, Reservation(claseNombre, "Hoy", hora, sala, "Confirmada"))
    }
}