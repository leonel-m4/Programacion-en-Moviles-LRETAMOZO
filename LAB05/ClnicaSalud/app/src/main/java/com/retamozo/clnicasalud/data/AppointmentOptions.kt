package com.retamozo.clnicasalud.data

data class DateOption(val diaCorto: String, val diaNumero: Int, val diaCompleto: String)
data class TimeOption(val chipLabel: String, val displayLabel: String)

val dateOptions = listOf(
    DateOption("Jue", 26, "Jueves"),
    DateOption("Vie", 27, "Viernes"),
    DateOption("Sáb", 28, "Sábado")
)

val timeOptions = listOf(
    TimeOption("9:00", "9:00 am"),
    TimeOption("10:30", "10:30 am"),
    TimeOption("3:00", "3:00 pm")
)