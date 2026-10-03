package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Control de cantidad estilizado ("− cantidad +") para el carrito y detalle de producto.
 */
@Composable
fun SelectorCantidad(
    cantidad: Int,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    modifier: Modifier = Modifier,
    minimo: Int = 1
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BotonCirculo(
            icono = Icons.Default.Remove,
            habilitado = cantidad > minimo,
            relleno = false,
            onClick = onDecrementar
        )

        Box(
            modifier = Modifier.size(width = 36.dp, height = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$cantidad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        BotonCirculo(
            icono = Icons.Default.Add,
            habilitado = true,
            relleno = true,
            onClick = onIncrementar
        )
    }
}

@Composable
private fun BotonCirculo(
    icono: ImageVector,
    habilitado: Boolean,
    relleno: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = habilitado,
        modifier = Modifier
            .size(28.dp)
            .background(
                color = when {
                    relleno -> VerdeBodega
                    habilitado -> MaterialTheme.colorScheme.surface
                    else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                },
                shape = CircleShape
            )
    ) {
        val colorIcono = when {
            relleno -> MaterialTheme.colorScheme.onPrimary
            habilitado -> VerdeBodega
            else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        }
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(16.dp)
        )
    }
}
