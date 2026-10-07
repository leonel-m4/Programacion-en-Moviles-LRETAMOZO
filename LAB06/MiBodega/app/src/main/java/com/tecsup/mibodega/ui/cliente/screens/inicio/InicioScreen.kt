package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Cuerpo del catálogo con filtro en tiempo real. Las barras las administra ClienteScaffold.
 */
@Composable
fun InicioScreen(
    productos: List<Producto> = listaProductosFake,
    favoritos: Set<Int>,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onFavoritoClick: (Producto) -> Unit
) {
    var categoriaSeleccionada by rememberSaveable { mutableStateOf(listaCategorias.first()) }
    var textoBusqueda by rememberSaveable { mutableStateOf("") }
    var ordenPrecio by rememberSaveable { mutableStateOf("Normal") }

    // FILTRADO EN TIEMPO REAL: Categoría + Texto de Búsqueda
    val productosFiltrados = productos
        .filter { producto ->
            val coincideCategoria =
                categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada
            val coincideBusqueda =
                textoBusqueda.isBlank() || producto.nombre.contains(
                    textoBusqueda.trim(),
                    ignoreCase = true
                )

            coincideCategoria && coincideBusqueda
        }
        .let { lista ->
            when (ordenPrecio) {
                "Menor" -> lista.sortedBy { it.precio }
                "Mayor" -> lista.sortedByDescending { it.precio }
                else -> lista
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Buscador en Tiempo Real
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            placeholder = {
                Text(
                    text = "Buscar en Mi Bodega (ej. Arroz, Coca...)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = VerdeBodega
                )
            },
            trailingIcon = {
                if (textoBusqueda.isNotEmpty()) {
                    IconButton(onClick = { textoBusqueda = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpiar búsqueda"
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                focusedBorderColor = VerdeBodega
            )
        )

        Text(
            text = "Categorías de productos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )

        // Categorías con íconos
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(listaCategorias) { categoria ->
                val icono = when (categoria) {
                    "Bebidas" -> Icons.Default.LocalBar
                    "Abarrotes" -> Icons.Default.Category
                    "Snacks" -> Icons.Default.Fastfood
                    else -> Icons.Default.ShoppingBag
                }
                ChipCategoria(
                    texto = categoria,
                    icono = icono,
                    seleccionado = categoria == categoriaSeleccionada,
                    onClick = { categoriaSeleccionada = categoria }
                )
            }
        }

        // Chips de orden por precio
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            ChipOrden(
                texto = "Precio ↑",
                seleccionado = ordenPrecio == "Menor",
                onClick = { ordenPrecio = if (ordenPrecio == "Menor") "Normal" else "Menor" }
            )
            ChipOrden(
                texto = "Precio ↓",
                seleccionado = ordenPrecio == "Mayor",
                onClick = { ordenPrecio = if (ordenPrecio == "Mayor") "Normal" else "Mayor" }
            )
        }

        Spacer(Modifier.height(4.dp))

        if (productosFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "No encontramos resultados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "No hay productos que coincidan con '$textoBusqueda' en la categoría '$categoriaSeleccionada'.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(productosFiltrados.chunked(2)) { fila ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        fila.forEach { producto ->
                            ProductoCard(
                                producto = producto,
                                onClick = { onProductoClick(producto) },
                                onAgregar = { onAgregarProducto(producto) },
                                modifier = Modifier.weight(1f),
                                esFavorito = favoritos.contains(producto.id),
                                onFavoritoClick = { onFavoritoClick(producto) }
                            )
                        }
                        if (fila.size == 1) {
                            Spacer(modifier = Modifier.weight(1f).width(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipCategoria(
    texto: String,
    icono: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo by animateColorAsState(
        targetValue = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        label = "ChipFondoAnim"
    )
    val contenido by animateColorAsState(
        targetValue = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        label = "ChipTextoAnim"
    )
    val escala by animateFloatAsState(
        targetValue = if (seleccionado) 1.05f else 1.0f,
        label = "ChipEscalaAnim"
    )

    Row(
        modifier = Modifier
            .scale(escala)
            .background(fondo, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = contenido,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = texto,
            color = contenido,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ChipOrden(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val textoColor = if (seleccionado) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .background(fondo, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            color = textoColor,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InicioPreview() {
    BodegaTheme {
        InicioScreen(
            favoritos = setOf(1),
            onProductoClick = {},
            onAgregarProducto = {},
            onFavoritoClick = {}
        )
    }
}
