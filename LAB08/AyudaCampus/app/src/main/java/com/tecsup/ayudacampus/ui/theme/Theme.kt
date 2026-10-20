package com.tecsup.ayudacampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.Rol

private val ClienteClaro = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E40AF),
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    background = BackgroundLight,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = OutlineBorder,
    error = ErrorRed
)

private val AdminClaro = lightColorScheme(
    primary = AdminPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE4FF),
    onPrimaryContainer = Color(0xFF5B21B6),
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    background = AdminBackground,
    surface = AdminSurface,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = OutlineBorder,
    error = ErrorRed
)

private val ClienteOscuro = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Color(0xFF1E3A8A),
    primaryContainer = Color(0xFF1D4ED8),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFF2DD4BF),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF334155),
    error = Color(0xFFF87171)
)

private val AdminOscuro = darkColorScheme(
    primary = Color(0xFFC4B5FD),
    onPrimary = Color(0xFF4C1D95),
    primaryContainer = Color(0xFF6D28D9),
    onPrimaryContainer = Color(0xFFEDE4FF),
    secondary = Color(0xFF2DD4BF),
    background = Color(0xFF0F0F1A),
    surface = Color(0xFF1A1829),
    onSurface = Color(0xFFFAF8FF),
    onSurfaceVariant = Color(0xFF9CA3AF),
    outline = Color(0xFF374151),
    error = Color(0xFFF87171)
)

@Composable
fun AyudaTheme(rol: Rol, oscuro: Boolean = false, content: @Composable () -> Unit) {
    val esquema = when {
        rol == Rol.ADMIN && oscuro -> AdminOscuro
        rol == Rol.ADMIN -> AdminClaro
        oscuro -> ClienteOscuro
        else -> ClienteClaro
    }
    MaterialTheme(colorScheme = esquema, typography = Typography, content = content)
}
