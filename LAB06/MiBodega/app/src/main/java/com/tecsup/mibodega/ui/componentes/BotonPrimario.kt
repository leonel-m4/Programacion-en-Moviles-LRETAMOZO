package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Botón principal moderno de la aplicación con esquinas redondeadas y soporte de íconos.
 */
@Composable
fun BotonPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtexto: String? = null,
    icono: Painter? = null,
    vectorIcono: ImageVector? = null,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier
            .fillMaxWidth()
            .height(if (subtexto != null) 60.dp else 52.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icono != null) {
                Icon(
                    painter = icono,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
            } else if (vectorIcono != null) {
                Icon(
                    imageVector = vectorIcono,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = texto,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (subtexto != null) 18.sp else 16.sp
                )
                if (subtexto != null) {
                    Text(
                        text = subtexto,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
