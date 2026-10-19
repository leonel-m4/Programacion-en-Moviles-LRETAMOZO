package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Estrellas(
    valor: Int,
    modifier: Modifier = Modifier,
    onCambiar: ((Int) -> Unit)? = null
) {
    Row(modifier) {
        (1..5).forEach { n ->
            Icon(
                imageVector = if (n <= valor) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = "$n estrellas",
                tint = Color(0xFFF59E0B),
                modifier = Modifier
                    .size(36.dp)
                    .then(if (onCambiar != null) Modifier.clickable { onCambiar(n) } else Modifier)
            )
        }
    }
}