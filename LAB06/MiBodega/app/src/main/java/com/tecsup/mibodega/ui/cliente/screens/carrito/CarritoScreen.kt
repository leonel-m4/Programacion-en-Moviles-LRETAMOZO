package com.tecsup.mibodega.ui.cliente.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.SelectorCantidad
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

private const val COSTO_DELIVERY = 4.00

/**
 * Pantalla 5: Mi Carrito rediseñada con miniaturas de productos,
 * resumen con desglose visual y diálogo de confirmación para eliminar.
 */
@Composable
fun CarritoScreen(
    carrito: List<ItemCarrito>,
    onVolver: () -> Unit,
    onIncrementar: (Producto) -> Unit,
    onDecrementar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onContinuarPedido: () -> Unit
) {
    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
    val delivery = if (carrito.isEmpty()) 0.00 else COSTO_DELIVERY
    val total = subtotal + delivery
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        EncabezadoCarrito(
            cantidadItems = carrito.sumOf { it.cantidad },
            onVolver = onVolver
        )

        if (carrito.isEmpty()) {
            // Estado Vacío estilizado
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RemoveShoppingCart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Explora nuestro catálogo y agrega tus productos favoritos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onVolver,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeBodega)
                    ) {
                        Text("Explorar productos", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(carrito, key = { it.producto.id }) { item ->
                    FilaCarrito(
                        item = item,
                        onIncrementar = { onIncrementar(item.producto) },
                        onDecrementar = { onDecrementar(item.producto) },
                        onEliminar = { productoAEliminar = item.producto }
                    )
                }
            }
        }

        if (carrito.isNotEmpty()) {
            ResumenYBoton(
                subtotal = subtotal,
                delivery = delivery,
                total = total,
                carritoVacio = false,
                onContinuarPedido = onContinuarPedido
            )
        }

        val producto = productoAEliminar
        if (producto != null) {
            AlertDialog(
                onDismissRequest = { productoAEliminar = null },
                title = { Text("Eliminar del carrito", fontWeight = FontWeight.Bold) },
                text = { Text("¿Deseas quitar '${producto.nombre}' de tu carrito?") },
                confirmButton = {
                    Button(
                        onClick = {
                            onEliminar(producto)
                            productoAEliminar = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { productoAEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
private fun EncabezadoCarrito(cantidadItems: Int, onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onVolver,
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = CircleShape
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = "Mi Carrito",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = if (cantidadItems > 0) "$cantidadItems productos seleccionados" else "Sin productos",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FilaCarrito(
    item: ItemCarrito,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    val subtotalFila = item.producto.precio * item.cantidad

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Miniatura del Producto
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.producto.imagenRes),
                    contentDescription = item.producto.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = "S/ %.2f c/u".format(item.producto.precio),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Subtotal: S/ %.2f".format(subtotalFila),
                    style = MaterialTheme.typography.labelMedium,
                    color = RojoPrecio,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Eliminar ${item.producto.nombre}",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.height(6.dp))

                SelectorCantidad(
                    cantidad = item.cantidad,
                    onIncrementar = onIncrementar,
                    onDecrementar = onDecrementar
                )
            }
        }
    }
}

@Composable
private fun ResumenYBoton(
    subtotal: Double,
    delivery: Double,
    total: Double,
    carritoVacio: Boolean,
    onContinuarPedido: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            FilaResumen(etiqueta = "Subtotal productos", valor = subtotal)
            Spacer(Modifier.height(4.dp))
            FilaResumen(etiqueta = "Costo de envío (Delivery)", valor = delivery)

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total a Pagar",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "S/ %.2f".format(total),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 22.sp,
                    color = VerdeBodega,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(16.dp))

            BotonPrimario(
                texto = "Continuar Pedido",
                vectorIcono = Icons.Default.ShoppingCartCheckout,
                onClick = onContinuarPedido,
                habilitado = !carritoVacio
            )
        }
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "S/ %.2f".format(valor),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CarritoPreview() {
    val carritoEjemplo = listOf(
        ItemCarrito(listaProductosFake[4], 1), // Coca-Cola
        ItemCarrito(listaProductosFake[0], 2), // Arroz Costeño
        ItemCarrito(listaProductosFake[2], 1)  // Leche Gloria
    )
    BodegaTheme {
        CarritoScreen(
            carrito = carritoEjemplo,
            onVolver = {},
            onIncrementar = {},
            onDecrementar = {},
            onEliminar = {},
            onContinuarPedido = {}
        )
    }
}
