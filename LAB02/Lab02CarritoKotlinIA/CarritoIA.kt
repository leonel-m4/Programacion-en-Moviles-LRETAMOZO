package com.retamozo.lab02carritokotlinia

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayList
import java.util.Locale
import java.util.Scanner

/**
 * =========================================================================
 * 0. CONSTANTES DE NEGOCIO
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
 * 3. MODELO DE DATOS (POO + ENCAPSULAMIENTO + POLIMORFISMO)
 * =========================================================================
 */
abstract class Product(
    val name: String,
    val price: BigDecimal,
    var quantity: Int,
    protected val taxLogic: Taxable
) {
    open fun calculateLineTotal(): BigDecimal {
        val base = price.multiply(BigDecimal(quantity))
        val tax = taxLogic.calculateTax(base)
        return base.add(tax)
    }

    abstract fun getCategoryName(): String

    fun getFormattedLine(): String {
        val lineTotal = price.multiply(BigDecimal(quantity))
        return java.lang.String.format(Locale.US, "[%-10s] %-20s x%d  S/ %8.2f", 
            getCategoryName(), name, quantity, lineTotal)
    }
}

class TechProduct(name: String, price: BigDecimal, quantity: Int)
    : Product(name, price, quantity, StandardTax()) {
    override fun getCategoryName(): String = "TECH"
}

class OfficeProduct(name: String, price: BigDecimal, quantity: Int)
    : Product(name, price, quantity, StandardTax()) {
    override fun getCategoryName(): String = "OFFICE"
}

/**
 * =========================================================================
 * 4. GESTOR DE SESIÓN DE COMPRA
 * =========================================================================
 */
class ShoppingSession(
    val customerName: String,
    private val discountStrategy: DiscountStrategy
) {
    private val _items = ArrayList<Product>()
    val items: List<Product> get() = _items

    fun addProduct(product: Product) {
        _items.add(product)
    }

    fun calculateSummary(): Summary {
        val subtotal = _items.sumOfBigDecimal { p -> p.price.multiply(BigDecimal(p.quantity)) }
        val totalTaxes = _items.sumOfBigDecimal { p ->
            p.calculateLineTotal().subtract(p.price.multiply(BigDecimal(p.quantity)))
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

inline fun <T> Iterable<T>.sumOfBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
    for (element in this) {
        sum = sum.add(selector(element))
    }
    return sum
}

/**
 * =========================================================================
 * 5. PUNTO DE ENTRADA (MAIN INTERACTIVO)
 * =========================================================================
 */
fun main() {
    val reader = Scanner(System.`in`).useLocale(Locale.US)
    
    System.out.println("====================================================")
    System.out.println("   TECSUP STORE - SISTEMA DE VENTAS INTERACTIVO    ")
    System.out.println("====================================================")

    val session = ShoppingSession("Leonel Retamozo", TecsupDiscountStrategy())

    System.out.print("¿Cuántos productos desea registrar? ")
    val n = if (reader.hasNextInt()) reader.nextInt() else 0
    if (reader.hasNextLine()) reader.nextLine() 

    for (i in 1..n) {
        System.out.println("\nProducto #$i:")
        System.out.print("Nombre: ")
        val name = reader.nextLine()
        
        System.out.print("Precio (ejm: 1500.50): ")
        val price = if (reader.hasNextBigDecimal()) reader.nextBigDecimal() else BigDecimal.ZERO
        
        System.out.print("Cantidad: ")
        val qty = if (reader.hasNextInt()) reader.nextInt() else 0
        
        System.out.println("Categoría: (1) Tecnología, (2) Oficina")
        System.out.print("Selección: ")
        val type = if (reader.hasNextInt()) reader.nextInt() else 1
        if (reader.hasNextLine()) reader.nextLine() 

        val product = if (type == 1) {
            TechProduct(name, price, qty)
        } else {
            OfficeProduct(name, price, qty)
        }
        
        session.addProduct(product)
    }

    System.out.println("\n\nGenerando comprobante...")
    System.out.println("============================================================")
    System.out.println("Cliente: ${session.customerName}")
    System.out.println("------------------------------------------------------------")
    
    val currentItems = session.items
    for (i in 0 until currentItems.size) {
        val p = currentItems[i]
        System.out.println("${i + 1}. ${p.getFormattedLine()}")
    }
    
    System.out.println("------------------------------------------------------------")
    val summary = session.calculateSummary()

    System.out.println(java.lang.String.format(Locale.US, "Subtotal:            S/ %10.2f", summary.subtotal))
    System.out.println(java.lang.String.format(Locale.US, "IGV (18%%):           S/ %10.2f", summary.totalTaxes))
    System.out.println(java.lang.String.format(Locale.US, "Descuento:          -S/ %10.2f", summary.discount))
    System.out.println("------------------------------------------------------------")
    System.out.println(java.lang.String.format(Locale.US, "TOTAL NETO:          S/ %10.2f", summary.totalNeto))

    var maxProduct: Product? = null
    for (p in currentItems) {
        if (maxProduct == null || p.price.compareTo(maxProduct.price) > 0) {
            maxProduct = p
        }
    }

    if (maxProduct != null) {
        System.out.println("\nInversión principal: ${maxProduct.name} (${maxProduct.getCategoryName()})")
    }
    System.out.println("============================================================")
}
