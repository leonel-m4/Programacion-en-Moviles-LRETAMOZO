package com.retamozo.sistemdemultas_leonelretamozo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(){
    println("=========================================")
    println("Sistema de Multas")
    println("=========================================")

    print("Titulo del libro: ")
    val nombreLibro = readLine()!!
    println()

    print("Nombre del usuario: ")
    val nombreUsuario = readLine()!!
    println()

    println("Tipo de usuario:")
    println("1. Alumno")
    println("2. Docente")
    print("Selecciona el tipo de usuario: ")
    val opcionTipo=readLine()!!.toInt()
    val tipoUsuario = when(opcionTipo){
        1 -> "Alumno"
        2 -> "Docente"
        else -> "No válido"
    }
    println()

    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    print("Fecha de prestamo(dd/MM/yyyy): ")
    val FechaPrestamo = LocalDate.parse(readLine()!!,formato)
    print("Fecha de entrega(dd/MM/yyyy): ")
    val FechaEntrega = LocalDate.parse(readLine()!!,formato)
    print("Fecha de devolucion(dd/MM/yyyy): ")
    val FechaDevolucion = LocalDate.parse(readLine()!!,formato)
    println()

    println("Titulo del libro: $nombreLibro")
    println("Nombre del usuario: $nombreUsuario")
    println("Tipo de usuario: $tipoUsuario")
    println("Fecha de prestamo: $FechaPrestamo")
    println("Fecha de entrega: $FechaEntrega")
    println("Fecha de devolucion: $FechaDevolucion")
}

data class PrestamoLibro(
    val nombreLibro: String,
    val nombreUsuario: String,
    val tipoUsuario:String,
    val FechaPrestamo: LocalDate,
    val FechaDevolucion: LocalDate,
    val FechaEntrega: LocalDate,
)