package com.retamozo.lib

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Leonel Retamozo"
    val carrito = mutableListOf<ProductoTest>()
    carrito.add(ProductoTest("Laptop HP", 2500.0, 1))
    carrito.add(ProductoTest("Mouse Logitech", 45.5, 2))
    carrito.add(ProductoTest("Teclado Redragon", 120.0, 6))
    carrito.add(ProductoTest("Audífonos Sony", 180.0, 4))

    mostrarDetalleTest(carrito)
    val subtotal = calcularSubtotalTest(carrito)
    val igv = calcularIGVTest(subtotal)
    val total = calcularTotalTest(subtotal,igv)

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }
    val descuento = calcularDescuentoTest(total)
    val totalDesc = total - descuento

    println(String.format("Subtotal: S/ %.2f",subtotal))
    println(String.format("IGV (18%%): S/ %.2f",igv))
    println(String.format("TOTAL: S/ %.2f",total))
    println(String.format("Descuento: S/ %.2f",descuento))
    println(String.format("Total con descuento: S/ %.2f",totalDesc))
}

data class ProductoTest(val nombre: String, val precio: Double, var cantidad: Int)

fun calcularSubtotalTest(productos: List<ProductoTest>) = productos.sumOf { it.precio * it.cantidad }
fun calcularIGVTest(subtotal: Double) = subtotal * 0.18
fun calcularTotalTest(subtotal: Double, igv: Double) = subtotal + igv
fun mostrarDetalleTest(productos: List<ProductoTest>) {
    println("--------- DETALLE DEL CARRITO ---------")
    productos.forEachIndexed { i, p -> 
        println(String.format("%d. %-20s x%d S/ %8.2f", i+1, p.nombre, p.cantidad, p.precio * p.cantidad))
    }
    println("---------------------------------------")
}
fun calcularDescuentoTest(total: Double) = when {
    total > 5000 -> total * 0.10
    total > 3000 -> total * 0.05
    else -> 0.0
}
