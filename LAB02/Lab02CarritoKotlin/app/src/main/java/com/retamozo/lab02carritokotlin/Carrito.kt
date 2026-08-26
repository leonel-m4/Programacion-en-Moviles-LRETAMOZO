package com.retamozo.lab02carritokotlin

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Juan Leon" // String (inferido)
    val carrito = mutableListOf<Producto>() // lista vacía de productos
    println("Cliente: $nombreCliente")
    println()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Redragon", 120.0, 6))
    carrito.add(Producto("Audífonos Sony", 180.0, 4))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
}

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)