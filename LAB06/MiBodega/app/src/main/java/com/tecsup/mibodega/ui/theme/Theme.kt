package com.tecsup.mibodega.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BodegaColorScheme = lightColorScheme(
    primary = VerdePrimario,
    onPrimary = Blanco,
    primaryContainer = VerdeSuaveFondo,
    onPrimaryContainer = VerdeOscuro,
    secondary = AzulEnlace,
    onSecondary = Blanco,
    background = FondoClaro,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisFondoContenedor,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

private val BodegaDarkColorScheme = darkColorScheme(
    primary = VerdePrimario,
    onPrimary = Blanco,
    primaryContainer = ContenedorOscuro,
    onPrimaryContainer = VerdeSuaveFondo,
    secondary = AzulEnlace,
    onSecondary = Blanco,
    background = FondoOscuro,
    onBackground = Blanco,
    surface = SuperficieOscura,
    onSurface = Blanco,
    surfaceVariant = ContenedorOscuro,
    onSurfaceVariant = GrisBorde,
    outline = GrisTexto,
    error = RojoPrecio
)

@Composable
fun BodegaTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) BodegaDarkColorScheme else BodegaColorScheme,
        typography = BodegaTypography,
        content = content
    )
}
