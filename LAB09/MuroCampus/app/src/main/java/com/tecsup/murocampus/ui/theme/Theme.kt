package com.tecsup.murocampus.ui.theme

import androidx.compose.material3.MaterialTheme
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

@Composable
fun MuroCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColoresMuro,
        typography = TipografiaMuro,
        content = content
    )
}
