package com.retamozo.lab02carritokotlin

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")
}

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)