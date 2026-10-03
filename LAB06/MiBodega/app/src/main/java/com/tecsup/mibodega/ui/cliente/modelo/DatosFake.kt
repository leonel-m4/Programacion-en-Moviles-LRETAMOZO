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
        unidad = "1 kg",
        imagenRes = R.drawable.ic_producto_arroz
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        descripcion = "Aceite vegetal 1 L, alto en vitamina E.",
        precio = 8.90,
        categoria = "Abarrotes",
        unidad = "1 L",
        imagenRes = R.drawable.ic_producto_aceite
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        descripcion = "Leche evaporada entera 1 L.",
        precio = 5.20,
        categoria = "Abarrotes",
        unidad = "1 L",
        imagenRes = R.drawable.ic_producto_leche
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        descripcion = "Galletas de chocolate rellenas 126 g.",
        precio = 3.50,
        categoria = "Snacks",
        unidad = "126 g",
        imagenRes = R.drawable.ic_producto_oreo
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        unidad = "1.5 L",
        imagenRes = R.drawable.ic_producto_coca
    )
)
