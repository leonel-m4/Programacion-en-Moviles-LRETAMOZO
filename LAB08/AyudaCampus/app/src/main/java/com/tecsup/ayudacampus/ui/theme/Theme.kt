package com.tecsup.ayudacampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.Rol

private val ClienteClaro = lightColorScheme(
    primary = Color(0xFF0891B2),
    primaryContainer = Color(0xFFCFFAFE),
    background = Color(0xFFF7FDFE),
    surface = Color(0xFFF7FDFE)
)

private val AdminClaro = lightColorScheme(
    primary = Color(0xFF7C3AED),
    primaryContainer = Color(0xFFEDE4FF),
    background = Color(0xFFFAF8FF),
    surface = Color(0xFFFAF8FF)
)

private val ClienteOscuro = darkColorScheme(
    primary = Color(0xFF22D3EE),
    primaryContainer = Color(0xFF0E4F5C),
    onPrimaryContainer = Color(0xFFCFFAFE),
    background = Color(0xFF0B1416),
    surface = Color(0xFF0B1416),
    surfaceVariant = Color(0xFF1F2A2D)
)

private val AdminOscuro = darkColorScheme(
    primary = Color(0xFFA78BFA),
    primaryContainer = Color(0xFF3B2A78),
    onPrimaryContainer = Color(0xFFEDE4FF),
    background = Color(0xFF110E1A),
    surface = Color(0xFF110E1A),
    surfaceVariant = Color(0xFF241F33)
)

@Composable
fun AyudaTheme(rol: Rol, oscuro: Boolean = false, content: @Composable () -> Unit) {
    val esquema = when {
        rol == Rol.ADMIN && oscuro -> AdminOscuro
        rol == Rol.ADMIN -> AdminClaro
        oscuro -> ClienteOscuro
        else -> ClienteClaro
    }
    MaterialTheme(colorScheme = esquema, content = content)
}