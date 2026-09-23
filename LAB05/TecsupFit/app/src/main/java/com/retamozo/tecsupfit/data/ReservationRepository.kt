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
        ),
        Reservation(
            claseNombre = "Cross Training",
            fecha = "Hoy",
            hora = "6:00 pm",
            sala = "Sala 1",
            estado = "Confirmada"
        )
    )

    fun reservar(claseNombre: String, hora: String, sala: String) {
        reservas.add(0, Reservation(claseNombre, "Hoy", hora, sala, "Confirmada"))
    }

    fun cancelarReserva(reservation: Reservation) {
        reservas.remove(reservation)
    }
}
