package com.retamozo.lab02carritokotlinia

/**
 * =========================================================================
 * 1. ABSTRACCIÓN Y POLIMORFISMO DE INTERFAZ
 * Definimos un contrato para cualquier cosa que pueda calcular impuestos.
 * =========================================================================
 */
interface Taxable {
    fun calculateTax(amount: Double): Double
}

class StandardTax : Taxable {
    override fun calculateTax(amount: Double): Double = amount * 0.18
}

/**
 * =========================================================================
 * 2. PATRÓN STRATEGY PARA DESCUENTOS
 * Esto permite cambiar la lógica de descuentos sin modificar el carrito.
 * =========================================================================
 */
interface DiscountStrategy {
    fun calculateDiscount(total: Double): Double
}

class TecsupDiscountStrategy : DiscountStrategy {
    override fun calculateDiscount(total: Double): Double {
        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }
}

/**
 * =========================================================================
 * 3. MODELO DE DATOS (POO + ENCAPSULAMIENTO)
 * =========================================================================
 */
abstract class Product(
    val name: String,
    val price: Double,
    var quantity: Int,
    private val taxLogic: Taxable // Inyección de comportamiento
) {
    fun getTotalWithTax(): Double {
        val base = price * quantity
        return base + taxLogic.calculateTax(base)
    }

    fun getFormattedLine(): String {
        return String.format("%-20s x%d  S/ %8.2f", name, quantity, price * quantity)
    }
}

class TechProduct(name: String, price: Double, quantity: Int) 
    : Product(name, price, quantity, StandardTax())

/**
 * =========================================================================
 * 4. GESTOR DE SESIÓN DE COMPRA (ENCAPSULAMIENTO ROBUSTO)
 * =========================================================================
 */
class ShoppingSession(
    val customerName: String,
    private val discountStrategy: DiscountStrategy
) {
    // Backing property: La lista es privada y mutable internamente,
    // pero se expone como una lista inmutable hacia afuera.
    private val _items = mutableListOf<Product>()
    val items: List<Product> get() = _items

    fun addProduct(product: Product) {
        _items.add(product)
    }

    fun getSubtotal(): Double = _items.sumOf { it.price * it.quantity }
    
    fun getTotalTaxes(): Double = _items.sumOf { it.getTotalWithTax() - (it.price * it.quantity) }
    
    fun getTotalFinal(): Double {
        val totalBruto = _items.sumOf { it.getTotalWithTax() }
        val descuento = discountStrategy.calculateDiscount(totalBruto)
        return totalBruto - descuento
    }

    fun getDiscountAmount(): Double {
        return discountStrategy.calculateDiscount(_items.sumOf { it.getTotalWithTax() })
    }
}

/**
 * =========================================================================
 * 5. PUNTO DE ENTRADA (MAIN)
 * =========================================================================
 */
fun main() {
    println("====================================================")
    println("   TECSUP STORE - ARQUITECTURA SOLID (KOTLIN)      ")
    println("====================================================")

    // Configuramos la sesión con una estrategia de descuento específica
    val session = ShoppingSession("Leonel Retamozo", TecsupDiscountStrategy())

    // Agregamos productos
    session.addProduct(TechProduct("Laptop HP", 2500.0, 1))
    session.addProduct(TechProduct("Mouse Logitech", 45.5, 2))
    session.addProduct(TechProduct("Teclado Redragon", 120.0, 6))
    session.addProduct(TechProduct("Audífonos Sony", 180.0, 4))

    // Detalle de la transacción
    println("Cliente: ${session.customerName}")
    println("---------------------------------------------")
    session.items.forEachIndexed { i, p ->
        println("${i + 1}. ${p.getFormattedLine()}")
    }
    println("---------------------------------------------")

    // Resumen Financiero
    val subtotal = session.getSubtotal()
    val igv = session.getTotalTaxes()
    val descuento = session.getDiscountAmount()
    val totalFinal = session.getTotalFinal()

    println(String.format("Subtotal:            S/ %10.2f", subtotal))
    println(String.format("IGV (18%%):           S/ %10.2f", igv))
    println(String.format("Descuento:          -S/ %10.2f", descuento))
    println("---------------------------------------------")
    println(String.format("TOTAL NETO:          S/ %10.2f", totalFinal))

    // Lógica de producto destacado
    session.items.maxByOrNull { it.price }?.let {
        println("\nInversión principal: ${it.name} (S/ ${it.price})")
    }

    println("\n[Arquitectura: Strategy Pattern & Interface Injection]")
}
