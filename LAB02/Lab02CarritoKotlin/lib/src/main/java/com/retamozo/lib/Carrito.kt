package com.retamozo.lib

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Leonel Retamozo" // String (inferido)
    val carrito = mutableListOf<Producto>() // lista vacía de productos
    println("Cliente: $nombreCliente")
    println()

    while (true){
        println("Ingrese el nombre del producto (o 'salir' para finalizar): ")
        val nombre = readln()
        if(nombre == "salir")break
        println("Ingrese el precio del producto: ")
        val precio = readln().toDouble()
        println("Ingrese la cantidad del producto: ")
        val cantidad = readln().toInt()
        carrito.add(Producto(nombre, precio, cantidad))
        println("Se agrego el producto")
    }

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }

    mostrarDetalle(carrito)
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal,igv)

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }
    val descuento = calcularDescuento(total)
    val totalDesc = total - descuento

    println(String.format("Subtotal: S/ %.2f",subtotal))
    println(String.format("IGV (18%%): S/ %.2f",igv))
    println(String.format("TOTAL: S/ %.2f",total))
    println(String.format("Descuento: S/ %.2f",descuento))
    println(String.format("Total con descuento: S/ %.2f",totalDesc))
}

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}
