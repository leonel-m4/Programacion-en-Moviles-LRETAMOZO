package com.retamozo.lab02carritokotlin

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Juan Leon" // String (inferido)
    val carrito = mutableListOf<Producto>() // lista vacía de productos
    println("Cliente: $nombreCliente")
    println()
}

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)