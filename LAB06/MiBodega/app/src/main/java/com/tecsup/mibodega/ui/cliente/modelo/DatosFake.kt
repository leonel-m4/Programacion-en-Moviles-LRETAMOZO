package com.tecsup.mibodega.ui.cliente.modelo

import com.tecsup.mibodega.R

/**
 * Datos de ejemplo (fake) para mostrar la UI.
 */
val listaCategorias = listOf("Todos", "Bebidas", "Abarrotes", "Snacks")

val listaProductosFake = listOf(
    Producto(
        id = 1,
        nombre = "Arroz Costeño",
        descripcion = "Arroz extra, grano largo, ideal para el día a día.",
        precio = 4.50,
        categoria = "Abarrotes",
        unidad = "750 g",
        imagenRes = R.drawable.producto_arroz
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        descripcion = "Aceite vegetal clásico, botella de 900 ml.",
        precio = 8.90,
        categoria = "Abarrotes",
        unidad = "900 ml",
        imagenRes = R.drawable.producto_aceite
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        descripcion = "Leche entera UHT, caja de 946 ml.",
        precio = 5.20,
        categoria = "Abarrotes",
        unidad = "946 ml",
        imagenRes = R.drawable.producto_leche
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        descripcion = "Galletas de chocolate con relleno original, paquete de 135 g.",
        precio = 3.50,
        categoria = "Snacks",
        unidad = "135 g",
        imagenRes = R.drawable.producto_oreo
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        unidad = "1.5 L",
        imagenRes = R.drawable.producto_coca
    )
)
