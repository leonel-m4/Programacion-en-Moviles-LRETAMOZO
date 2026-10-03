package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 8: Confirmación de Pedido realizada con éxito.
 */
@Composable
fun ConfirmacionScreen(
    total: Double,
    tipoEntrega: String,
    direccion: String = "Av. Los Olivos 123",
    referencia: String = "Frente al parque",
    numeroPedido: Int = 1,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(36.dp))

        // Ícono de Éxito
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = VerdeBodega,
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "¡Pedido Confirmado!",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 28.sp,
            color = VerdeBodega,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Tu pedido en Mi Bodega ha sido recibido con éxito y ya está en preparación.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(28.dp))

        // Tarjeta del Resumen del Pedido
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = null,
                        tint = VerdeBodega,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Pedido #%02d".format(numeroPedido),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                DatoFila(
                    etiqueta = "Total abonado",
                    valor = "S/ %.2f".format(total),
                    valorColor = RojoPrecio
                )

                Spacer(Modifier.height(8.dp))

                DatoFila(
                    etiqueta = "Modalidad de entrega",
                    valor = tipoEntrega
                )

                Spacer(Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = if (tipoEntrega == "Delivery") Icons.Default.LocalShipping else Icons.Default.PinDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Dirección de destino:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = direccion,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (referencia.isNotBlank()) {
                            Text(
                                text = "Ref: $referencia",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Volver al Inicio",
            vectorIcono = Icons.Default.Home,
            onClick = onVolverInicio
        )

        Spacer(Modifier.height(28.dp))
    }
}

@Composable
private fun DatoFila(
    etiqueta: String,
    valor: String,
    valorColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = valorColor
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConfirmacionPreview() {
    BodegaTheme {
        ConfirmacionScreen(
            total = 25.90,
            tipoEntrega = "Delivery",
            direccion = "Av. Los Olivos 123",
            referencia = "Frente al parque",
            onVolverInicio = {}
        )
    }
}
