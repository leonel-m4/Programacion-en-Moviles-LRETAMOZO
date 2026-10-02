package com.tecsup.mibodega.ui.componentes

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Tarjeta de producto con fotografía local, etiqueta de categoría,
 * indicador de favorito y botón flotante de agregar al carrito.
 */
@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAgregar: () -> Unit,
    modifier: Modifier = Modifier,
    esFavorito: Boolean = false,
    onFavoritoClick: () -> Unit = {}
) {
    val favoritoColor by animateColorAsState(
        targetValue = if (esFavorito) RojoPrecio else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        label = "FavoritoColorAnimation"
    )

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Contenedor de la Imagen con etiqueta y botón de favorito
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = producto.imagenRes),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )

                // Tag de Categoría en la esquina superior izquierda
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = producto.categoria,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                // Botón de Favorito en la esquina superior derecha
                IconButton(
                    onClick = onFavoritoClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp)
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.85f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = favoritoColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = producto.unidad,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.titleMedium,
                    color = RojoPrecio,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(
                    onClick = onAgregar,
                    modifier = Modifier
                        .size(34.dp)
                        .background(VerdeBodega, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar ${producto.nombre}",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
