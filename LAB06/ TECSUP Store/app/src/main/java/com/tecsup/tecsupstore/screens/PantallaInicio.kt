package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.Producto

@Composable
fun PantallaInicio(
    favoritosIds: Set<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    val productos = listOf(
        Producto(
            id = 1,
            nombre = "Audífonos",
            precio = 89.00
        ),
        Producto(
            id = 2,
            nombre = "Smartwatch",
            precio = 199.00
        ),
        Producto(
            id = 3,
            nombre = "Funda celular",
            precio = 25.00
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            4.dp
        )
    ) {
        items(productos, key = { it.id }) { producto ->
            TarjetaProducto(
                producto = producto,
                isFavorito = producto.id in favoritosIds,
                onToggleFavorito = {
                    onToggleFavorito(producto.id)
                }
            )
        }
    }
}
