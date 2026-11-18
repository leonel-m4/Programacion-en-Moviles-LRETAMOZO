package com.retamozo.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Selector del radio de cercanía. Solo informa el radio elegido; null = sin radio.
 */
@Composable
fun SelectorRadio(
    radios: List<Int>,
    seleccionado: Int?,
    onSeleccionar: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionado == null,
                onClick = { onSeleccionar(null) },
                label = { Text("Sin radio") }
            )
        }
        items(radios) { metros ->
            FilterChip(
                selected = seleccionado == metros,
                onClick = { onSeleccionar(metros) },
                label = { Text("$metros m") }
            )
        }
    }
}
