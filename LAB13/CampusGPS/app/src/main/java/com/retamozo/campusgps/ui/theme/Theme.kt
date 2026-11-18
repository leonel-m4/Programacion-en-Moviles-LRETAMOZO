package com.retamozo.campusgps.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
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

@Composable
fun CampusGPSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColoresCampus,
        typography = TipografiaCampus,
        content = content
    )
}
