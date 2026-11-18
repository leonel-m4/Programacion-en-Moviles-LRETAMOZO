package com.retamozo.campusgps.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val ColoresCampus = lightColorScheme(
    primary = AzulCampus,
    onPrimary = Color.White,
    primaryContainer = AzulSeleccion,
    onPrimaryContainer = TextoCampus,
    background = FondoCampus,
    onBackground = TextoCampus,
    surface = FondoCampus,
    onSurface = TextoCampus,
    surfaceVariant = FondoTarjeta,
    onSurfaceVariant = TextoSecundario
)

private val ColoresCampusOscuro = darkColorScheme(
    primary = AzulCampusOscuro,
    onPrimary = Color.White,
    primaryContainer = AzulSeleccionOscuro,
    onPrimaryContainer = TextoCampusOscuro,
    background = FondoCampusOscuro,
    onBackground = TextoCampusOscuro,
    surface = FondoCampusOscuro,
    onSurface = TextoCampusOscuro,
    surfaceVariant = FondoTarjetaOscuro,
    onSurfaceVariant = TextoSecundarioOscuro
)

// Permite que otros componentes (como el mapa) sepan si el tema activo es oscuro.
val LocalTemaOscuro = staticCompositionLocalOf { false }

@Composable
fun CampusGPSTheme(
    oscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalTemaOscuro provides oscuro) {
        MaterialTheme(
            colorScheme = if (oscuro) ColoresCampusOscuro else ColoresCampus,
            typography = TipografiaCampus,
            content = content
        )
    }
}
