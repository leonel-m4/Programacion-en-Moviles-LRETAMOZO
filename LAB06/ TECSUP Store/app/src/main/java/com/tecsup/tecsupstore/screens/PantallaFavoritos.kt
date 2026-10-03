package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.tecsupstore.Producto

@Composable
fun PantallaFavoritos(
    productosFavoritos: List<Producto>,
    favoritosIds: Set<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    if (productosFavoritos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes productos favoritos",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    } else {
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
            items(productosFavoritos, key = { it.id }) { producto ->
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
}
