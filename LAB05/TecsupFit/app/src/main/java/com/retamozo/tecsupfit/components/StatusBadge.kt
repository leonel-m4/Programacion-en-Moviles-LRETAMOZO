package com.retamozo.tecsupfit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatusBadge(estado: String) {
    val bg = if (estado == "Confirmada") Color(0xFFD7F5E3) else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (estado == "Confirmada") Color(0xFF1E7A46) else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier.clip(RoundedCornerShape(50)).background(bg).padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(estado, color = fg, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.labelSmall)
    }
}