package com.tecsup.murocampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColoresMuro = lightColorScheme(
    primary = AzulMuro,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    onPrimaryContainer = TextoMuro,
    background = FondoMuro,
    onBackground = TextoMuro,
    surface = BlancoTarjeta,
    onSurface = TextoMuro,
    surfaceVariant = GrisTarjeta,
    onSurfaceVariant = TextoSecundario,
    outlineVariant = BordeTarjeta,
    error = RojoError,
    errorContainer = FondoError,
    onErrorContainer = RojoError
)

private val DarkColoresMuro = darkColorScheme(
    primary = AzulMuro,
    onPrimary = Color.White,
    primaryContainer = AzulMuro,
    onPrimaryContainer = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFE3E3E3),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE3E3E3),
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFC4C7C5),
    outlineVariant = Color(0xFF444746),
    error = RojoError,
    errorContainer = FondoError,
    onErrorContainer = RojoError
)

@Composable
fun MuroCampusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColoresMuro else ColoresMuro
    MaterialTheme(
        colorScheme = colorScheme,
        typography = TipografiaMuro,
        content = content
    )
}
