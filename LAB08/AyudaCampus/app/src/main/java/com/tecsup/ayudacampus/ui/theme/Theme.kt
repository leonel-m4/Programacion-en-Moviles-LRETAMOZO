package com.tecsup.ayudacampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.Rol

private val EsquemaCliente = lightColorScheme(
    primary = Color(0xFF0891B2),
    primaryContainer = Color(0xFFCFFAFE),
    background = Color(0xFFF7FDFE),
    surface = Color(0xFFF7FDFE)
)

private val EsquemaAdmin = lightColorScheme(
    primary = Color(0xFF7C3AED),
    primaryContainer = Color(0xFFEDE4FF),
    background = Color(0xFFFAF8FF),
    surface = Color(0xFFFAF8FF)
)

@Composable
fun AyudaTheme(rol: Rol, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (rol == Rol.ADMIN) EsquemaAdmin else EsquemaCliente,
        content = content
    )
}