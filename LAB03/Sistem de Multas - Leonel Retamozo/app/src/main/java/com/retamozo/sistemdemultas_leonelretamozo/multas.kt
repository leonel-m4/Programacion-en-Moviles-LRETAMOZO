package com.retamozo.sistemdemultas_leonelretamozo
import java.time.LocalDate

fun main(){
    println("=========================================")
    println("Sistema de Multas")
    println("=========================================")
}

data class PrestamoLibro(
    val nombreLibro: String,
    val nombreUsuario: String,
    val FechaPrestamo: LocalDate,
    val FechaDevolucion: LocalDate,
    val FechaEntrega: LocalDate,
)