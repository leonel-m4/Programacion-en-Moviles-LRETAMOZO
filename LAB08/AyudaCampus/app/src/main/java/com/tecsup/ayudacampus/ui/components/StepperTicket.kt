package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket

@Composable
fun StepperTicket(estado: String, modifier: Modifier = Modifier) {
    val pasos = listOf(
        EstadoTicket.ABIERTO to "Abierto",
        EstadoTicket.EN_PROCESO to "En proceso",
        EstadoTicket.RESUELTO to "Resuelto",
        EstadoTicket.CERRADO to "Cerrado"
    )
    val actual = pasos.indexOfFirst { it.first.name == estado }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        pasos.forEachIndexed { i, (_, nombre) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .size(20.dp)
                        .background(
                            if (i <= actual) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant,
                            CircleShape
                        )
                )
                Text(nombre, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}