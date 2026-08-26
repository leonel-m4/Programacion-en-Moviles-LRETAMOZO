package com.retamozo.lab02carritokotlinia

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayList
import java.util.Locale

/**
 * =========================================================================
 * 0. CONSTANTES DE NEGOCIO
 * Evitamos "números mágicos" para mejorar la mantenibilidad.
 * =========================================================================
 */
object Constants {
    val TAX_RATE = BigDecimal("0.18")
    val HIGH_DISCOUNT_THRESHOLD = BigDecimal("5000.00")
    val HIGH_DISCOUNT_RATE = BigDecimal("0.10")
    val LOW_DISCOUNT_THRESHOLD = BigDecimal("3000.00")
    val LOW_DISCOUNT_RATE = BigDecimal("0.05")
}

/**
 * =========================================================================
 * 1. ABSTRACCIÓN Y POLIMORFISMO DE INTERFAZ
 * Definimos un contrato para cualquier cosa que pueda calcular impuestos.
 * =========================================================================
 */
interface Taxable {
    fun calculateTax(amount: BigDecimal): BigDecimal
}

class StandardTax : Taxable {
    override fun calculateTax(amount: BigDecimal): BigDecimal =
        amount.multiply(Constants.TAX_RATE).setScale(2, RoundingMode.HALF_UP)
}

/**
 * =========================================================================
 * 2. PATRÓN STRATEGY PARA DESCUENTOS
 * Esto permite cambiar la lógica de descuentos sin modificar el carrito.
 * =========================================================================
 */
interface DiscountStrategy {
    fun calculateDiscount(total: BigDecimal): BigDecimal
}

class TecsupDiscountStrategy : DiscountStrategy {
    override fun calculateDiscount(total: BigDecimal): BigDecimal {
        return when {
            total.compareTo(Constants.HIGH_DISCOUNT_THRESHOLD) > 0 ->
                total.multiply(Constants.HIGH_DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP)
            total.compareTo(Constants.LOW_DISCOUNT_THRESHOLD) > 0 ->
                total.multiply(Constants.LOW_DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP)
            else -> BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
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
    val price: BigDecimal,
    var quantity: Int,
    private val taxLogic: Taxable // Inyección de comportamiento
) {
    fun getTotalWithTax(): BigDecimal {
        val base = price.multiply(BigDecimal(quantity))
        return base.add(taxLogic.calculateTax(base))
    }

    fun getFormattedLine(): String {
        val lineTotal = price.multiply(BigDecimal(quantity))
        return java.lang.String.format(Locale.US, "%-20s x%d  S/ %8.2f", name, quantity, lineTotal)
    }
}

class TechProduct(name: String, price: BigDecimal, quantity: Int)
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
    private val _items = ArrayList<Product>()
    val items: List<Product> get() = _items

    fun addProduct(product: Product) {
        _items.add(product)
    }

    /**
     * Optimización: Cálculo consolidado para evitar redundancia y múltiples iteraciones.
     */
    fun calculateSummary(): Summary {
        val subtotal = _items.sumOfBigDecimal { p: Product -> p.price.multiply(BigDecimal(p.quantity)) }
        val totalTaxes = _items.sumOfBigDecimal { p: Product ->
            p.getTotalWithTax().subtract(p.price.multiply(BigDecimal(p.quantity)))
        }
        val totalBruto = subtotal.add(totalTaxes)
        val discount = discountStrategy.calculateDiscount(totalBruto)
        val totalNeto = totalBruto.subtract(discount)

        return Summary(subtotal, totalTaxes, discount, totalNeto)
    }

    data class Summary(
        val subtotal: BigDecimal,
        val totalTaxes: BigDecimal,
        val discount: BigDecimal,
        val totalNeto: BigDecimal
    )
}

/**
 * Extensión para sumOf con BigDecimal.
 * Se usa un nombre distinto para evitar conflictos de resolución en entornos sin SDK completo.
 */
inline fun <T> Iterable<T>.sumOfBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
    for (element in this) {
        sum = sum.add(selector(element))
    }
    return sum
}

/**
 * =========================================================================
 * 5. PUNTO DE ENTRADA (MAIN)
 * =========================================================================
 */
fun main() {
    System.out.println("====================================================")
    System.out.println("   TECSUP STORE - ARQUITECTURA SOLID (KOTLIN)      ")
    System.out.println("====================================================")

    // Configuramos la sesión con una estrategia de descuento específica
    val session = ShoppingSession("Leonel Retamozo", TecsupDiscountStrategy())

    // Agregamos productos (Usando BigDecimal para precisión financiera)
    session.addProduct(TechProduct("Laptop HP", BigDecimal("2500.00"), 1))
    session.addProduct(TechProduct("Mouse Logitech", BigDecimal("45.50"), 2))
    session.addProduct(TechProduct("Teclado Redragon", BigDecimal("120.00"), 6))
    session.addProduct(TechProduct("Audífonos Sony", BigDecimal("180.00"), 4))

    // Detalle de la transacción
    System.out.println("Cliente: ${session.customerName}")
    System.out.println("---------------------------------------------")
    val currentItems = session.items
    var index = 0
    for (p in currentItems) {
        System.out.println("${index + 1}. ${p.getFormattedLine()}")
        index++
    }
    System.out.println("---------------------------------------------")

    // Resumen Financiero optimizado
    val summary = session.calculateSummary()

    System.out.println(java.lang.String.format(Locale.US, "Subtotal:            S/ %10.2f", summary.subtotal))
    System.out.println(java.lang.String.format(Locale.US, "IGV (18%%):           S/ %10.2f", summary.totalTaxes))
    System.out.println(java.lang.String.format(Locale.US, "Descuento:          -S/ %10.2f", summary.discount))
    System.out.println("---------------------------------------------")
    System.out.println(java.lang.String.format(Locale.US, "TOTAL NETO:          S/ %10.2f", summary.totalNeto))

    // Lógica de producto destacado
    var maxProduct: Product? = null
    for (p in currentItems) {
        if (maxProduct == null || p.price.compareTo(maxProduct.price) > 0) {
            maxProduct = p
        }
    }

    if (maxProduct != null) {
        System.out.println("\nInversión principal: ${maxProduct.name} (S/ ${maxProduct.price})")
    }

    System.out.println("\n[Arquitectura: Strategy Pattern & Interface Injection & BigDecimal Precision]")
}
