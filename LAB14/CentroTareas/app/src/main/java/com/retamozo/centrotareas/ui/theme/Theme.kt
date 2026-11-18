package com.retamozo.centrotareas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColoresTareas = lightColorScheme(
    primary = AzulTareas,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    onPrimaryContainer = TextoTareas,
    background = FondoTareas,
    onBackground = TextoTareas,
    surface = BlancoTarjeta,
    onSurface = TextoTareas,
    onSurfaceVariant = TextoSecundario,
    outlineVariant = BordeTarjeta
)

@Composable
fun CentroTareasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColoresTareas,
        typography = TipografiaTareas,
        content = content
    )
}
