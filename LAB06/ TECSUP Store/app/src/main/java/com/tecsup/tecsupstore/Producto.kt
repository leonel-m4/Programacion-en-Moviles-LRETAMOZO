package com.tecsup.tecsupstore

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val descripcion: String,
    val destacado: Boolean = false,
    val stock: Int = 10,
    val rating: Double = 4.5,
    val etiqueta: String? = null
)

data class Pedido(
    val codigo: String,
    val fecha: String,
    val items: List<Producto>,
    val total: Double,
    val estado: String
)

data class Usuario(
    val nombre: String,
    val correo: String,
    val telefono: String,
    val direccion: String
)

val listaProductosControlada = listOf(
    Producto(1, "Laptop Pro 01", 3499.00, "Laptops", "Procesador i7, 16GB RAM, SSD 512GB.", true, 5, 4.9, "Top"),
    Producto(2, "Laptop Air 02", 2899.00, "Laptops", "Diseño ultradelgado, batería de larga duración.", false, 8, 4.7, "Oferta"),
    Producto(3, "Celular Max 03", 2299.00, "Celulares", "Pantalla AMOLED 120Hz, cámara de 108MP.", true, 10, 4.6, "Nuevo"),
    Producto(4, "Celular Lite 04", 1299.00, "Celulares", "Excelente rendimiento y cámara dual.", false, 12, 4.4),
    Producto(5, "Audífonos Pro 05", 289.00, "Audio", "Cancelación activa de ruido y sonido espacial.", true, 15, 4.8, "Top"),
    Producto(6, "Parlante Mini 06", 120.00, "Audio", "Resistente al agua IPX7 con graves potenciados.", false, 20, 4.3),
    Producto(7, "Mouse Gamer 07", 150.00, "Gaming", "Sensor óptico de alta precisión y luces RGB.", true, 18, 4.7, "Nuevo"),
    Producto(8, "Teclado Mecánico 08", 250.00, "Gaming", "Switches mecánicos táctiles y retroiluminación.", false, 14, 4.6),
    Producto(9, "Mochila Tech 09", 99.00, "Accesorios", "Impermeable con compartimento para laptop.", false, 30, 4.5),
    Producto(10, "Cargador Rápido 10", 79.00, "Accesorios", "Carga rápida USB-C de 65W GaN.", true, 25, 4.8, "Oferta"),
    Producto(11, "Tablet Plus 11", 1599.00, "Celulares", "Pantalla 11 pulgadas con lápiz óptico incluido.", false, 7, 4.5),
    Producto(12, "Smartwatch Fit 12", 299.00, "Accesorios", "Monitoreo de salud 24/7 y GPS integrado.", true, 12, 4.7, "Top")
)
