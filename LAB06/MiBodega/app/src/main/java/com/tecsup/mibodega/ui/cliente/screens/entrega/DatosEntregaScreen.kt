package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun DatosEntregaScreen(
    subtotal: Double,
    onVolver: () -> Unit,
    onConfirmarPedido: (tipoEntrega: String, costoEntrega: Double) -> Unit
) {
    var nombreCliente by remember { mutableStateOf("") }
    var telefonoCliente by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var referenciaCliente by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("Efectivo") }
    var tipoEntrega by remember { mutableStateOf("Delivery") }
    var mostrarErrores by remember { mutableStateOf(false) }
    val costoEntrega = if (tipoEntrega == "Delivery") 4.00 else 0.00
    val total = subtotal + costoEntrega

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Nombre",
            valor = nombreCliente,
            onValorCambia = { nombreCliente = it },
            placeholder = "Juan Pérez",
            esError = mostrarErrores && nombreCliente.isBlank()
        )
        Spacer(Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefonoCliente,
            onValorCambia = { telefonoCliente = it },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone,
            esError = mostrarErrores && telefonoCliente.isBlank()
        )
        Spacer(Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Dirección",
            valor = direccionCliente,
            onValorCambia = { direccionCliente = it },
            placeholder = "Av. Los Olivos 123",
            esError = mostrarErrores && direccionCliente.isBlank()
        )
        Spacer(Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Referencia",
            valor = referenciaCliente,
            onValorCambia = { referenciaCliente = it },
            placeholder = "Frente al parque",
            esError = mostrarErrores && referenciaCliente.isBlank()
        )

        if (mostrarErrores && listOf(nombreCliente, telefonoCliente, direccionCliente, referenciaCliente).any { it.isBlank() }) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Completa todos los campos para confirmar",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Tipo de entrega",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        OpcionPago(
            texto = "Delivery S/ 4.00",
            seleccionado = tipoEntrega == "Delivery",
            icono = { Icon(Icons.Default.LocalShipping, contentDescription = null, tint = VerdeBodega) },
            onClick = { tipoEntrega = "Delivery" }
        )
        OpcionPago(
            texto = "Recojo en tienda S/ 0.00",
            seleccionado = tipoEntrega == "Recojo en tienda",
            icono = { Icon(Icons.Default.Store, contentDescription = null, tint = VerdeBodega) },
            onClick = { tipoEntrega = "Recojo en tienda" }
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Método de pago",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        OpcionPago(
            texto = "Efectivo al entregar",
            seleccionado = metodoPago == "Efectivo",
            icono = { Icon(Icons.Default.Payments, contentDescription = null, tint = VerdeBodega) },
            onClick = { metodoPago = "Efectivo" }
        )
        OpcionPago(
            texto = "Yape",
            seleccionado = metodoPago == "Yape",
            icono = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = VerdeBodega) },
            onClick = { metodoPago = "Yape" }
        )
        OpcionPago(
            texto = "Plin",
            seleccionado = metodoPago == "Plin",
            icono = { Icon(Icons.Default.LocalShipping, contentDescription = null, tint = VerdeBodega) },
            onClick = { metodoPago = "Plin" }
        )

        Spacer(Modifier.height(14.dp))
        Text("Subtotal: S/ %.2f".format(subtotal))
        Text("Costo de entrega: S/ %.2f".format(costoEntrega))
        Text(
            text = "Total: S/ %.2f".format(total),
            style = MaterialTheme.typography.titleMedium,
            color = VerdeBodega
        )

        Spacer(Modifier.height(24.dp))

        BotonPrimario(
            texto = "Confirmar pedido",
            onClick = {
                mostrarErrores = true
                if (listOf(nombreCliente, telefonoCliente, direccionCliente, referenciaCliente).all { it.isNotBlank() }) {
                    onConfirmarPedido(tipoEntrega, costoEntrega)
                }
            }
        )
    }
}

@Composable
private fun OpcionPago(
    texto: String,
    seleccionado: Boolean,
    icono: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = seleccionado, onClick = onClick)
        icono()
        Spacer(Modifier.size(10.dp))
        Text(text = texto)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            subtotal = 21.90,
            onVolver = {},
            onConfirmarPedido = { _, _ -> }
        )
    }
}

