package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 7: Datos de Entrega y Método de Pago.
 * Si es Recojo en Tienda, no exige dirección ni referencia.
 */
@Composable
fun DatosEntregaScreen(
    subtotal: Double,
    nombreInicial: String = "",
    telefonoInicial: String = "",
    direccionInicial: String = "",
    referenciaInicial: String = "",
    onVolver: () -> Unit,
    onConfirmarPedido: (tipoEntrega: String, costoEntrega: Double, direccion: String, referencia: String) -> Unit
) {
    var nombreCliente by remember { mutableStateOf(nombreInicial) }
    var telefonoCliente by remember { mutableStateOf(telefonoInicial) }
    var direccionCliente by remember { mutableStateOf(direccionInicial) }
    var referenciaCliente by remember { mutableStateOf(referenciaInicial) }
    var metodoPago by remember { mutableStateOf("Efectivo") }
    var tipoEntrega by remember { mutableStateOf("Delivery") }
    var mostrarErrores by remember { mutableStateOf(false) }

    val esDelivery = tipoEntrega == "Delivery"
    val costoEntrega = if (esDelivery) 4.00 else 0.00
    val total = subtotal + costoEntrega

    val datosValidos = if (esDelivery) {
        nombreCliente.isNotBlank() && telefonoCliente.isNotBlank() &&
                direccionCliente.isNotBlank() && referenciaCliente.isNotBlank()
    } else {
        nombreCliente.isNotBlank() && telefonoCliente.isNotBlank()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            IconButton(
                onClick = onVolver,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = CircleShape
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Datos de Pedido",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(Modifier.height(16.dp))

        // Sección 1: Tipo de Entrega
        SeccionTitulo(titulo = "1. Tipo de Entrega")

        OpcionTarjeta(
            titulo = "Delivery a Domicilio",
            subtitulo = "Llevamos tu compra a tu casa (30-45 min)",
            precioTexto = "S/ 4.00",
            seleccionado = esDelivery,
            icono = Icons.Default.LocalShipping,
            onClick = { tipoEntrega = "Delivery" }
        )

        Spacer(Modifier.height(8.dp))

        OpcionTarjeta(
            titulo = "Recojo en Tienda",
            subtitulo = "Retiras en nuestra bodega sin costo de envío",
            precioTexto = "Gratis",
            seleccionado = !esDelivery,
            icono = Icons.Default.Store,
            onClick = { tipoEntrega = "Recojo en tienda" }
        )

        Spacer(Modifier.height(20.dp))

        // Sección 2: Datos del Receptor y Dirección
        SeccionTitulo(titulo = if (esDelivery) "2. Datos de Envío" else "2. Datos de quien Recoge")

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CampoTexto(
                    etiqueta = "Nombre de quien solicita / retira",
                    valor = nombreCliente,
                    iconoLeading = Icons.Default.Person,
                    onValorCambia = { nombreCliente = it },
                    placeholder = "Ej. Juan Pérez",
                    esError = mostrarErrores && nombreCliente.isBlank()
                )
                Spacer(Modifier.height(12.dp))

                CampoTexto(
                    etiqueta = "Teléfono de contacto",
                    valor = telefonoCliente,
                    iconoLeading = Icons.Default.Phone,
                    onValorCambia = { telefonoCliente = it },
                    placeholder = "Ej. 987 654 321",
                    teclado = KeyboardType.Phone,
                    esError = mostrarErrores && telefonoCliente.isBlank()
                )

                if (esDelivery) {
                    Spacer(Modifier.height(12.dp))
                    CampoTexto(
                        etiqueta = "Dirección de entrega",
                        valor = direccionCliente,
                        iconoLeading = Icons.Default.Home,
                        onValorCambia = { direccionCliente = it },
                        placeholder = "Ej. Av. Los Olivos 123",
                        esError = mostrarErrores && direccionCliente.isBlank()
                    )
                    Spacer(Modifier.height(12.dp))

                    CampoTexto(
                        etiqueta = "Referencia",
                        valor = referenciaCliente,
                        iconoLeading = Icons.Default.PinDrop,
                        onValorCambia = { referenciaCliente = it },
                        placeholder = "Ej. Frente al parque principal",
                        esError = mostrarErrores && referenciaCliente.isBlank()
                    )
                } else {
                    // Tarjeta Informativa de la Tienda para Recojo
                    Spacer(Modifier.height(14.dp))
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = VerdeBodega,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Punto de Recojo:",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Tienda Principal Mi Bodega — Av. Central 456, Lima\nHorario: 8:00 AM - 9:00 PM",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = mostrarErrores && !datosValidos) {
                    Column {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = if (esDelivery) "Completa nombre, teléfono, dirección y referencia para continuar." else "Ingresa nombre y teléfono de contacto para el recojo.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Sección 3: Método de Pago
        SeccionTitulo(titulo = "3. Método de Pago")

        OpcionTarjeta(
            titulo = "Efectivo",
            subtitulo = if (esDelivery) "Pagas en efectivo al recibir" else "Pagas en tienda al retirar",
            precioTexto = "",
            seleccionado = metodoPago == "Efectivo",
            icono = Icons.Default.Payments,
            onClick = { metodoPago = "Efectivo" }
        )

        Spacer(Modifier.height(8.dp))

        OpcionTarjeta(
            titulo = "Yape / Plin",
            subtitulo = "Pago móvil con QR al momento del pedido",
            precioTexto = "",
            seleccionado = metodoPago == "Yape",
            icono = Icons.Default.CreditCard,
            onClick = { metodoPago = "Yape" }
        )

        Spacer(Modifier.height(20.dp))

        // Resumen del Pedido
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumen de Pago",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal de productos", style = MaterialTheme.typography.bodyMedium)
                    Text("S/ %.2f".format(subtotal), fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Costo de envío ($tipoEntrega)", style = MaterialTheme.typography.bodyMedium)
                    Text(if (esDelivery) "S/ 4.00" else "Gratis", fontWeight = FontWeight.SemiBold)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total a Pagar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = "S/ %.2f".format(total),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp,
                        color = VerdeBodega,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        BotonPrimario(
            texto = "Confirmar Pedido",
            vectorIcono = Icons.Default.CheckCircle,
            onClick = {
                mostrarErrores = true
                if (datosValidos) {
                    val dirFinal = if (esDelivery) direccionCliente else "Tienda Principal Mi Bodega (Av. Central 456)"
                    val refFinal = if (esDelivery) referenciaCliente else "Recojo en Tienda"
                    onConfirmarPedido(tipoEntrega, costoEntrega, dirFinal, refFinal)
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SeccionTitulo(titulo: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun OpcionTarjeta(
    titulo: String,
    subtitulo: String,
    precioTexto: String,
    seleccionado: Boolean,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    val bordeColor = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val fondoColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = fondoColor),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.5.dp, color = bordeColor, shape = RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = seleccionado,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
            )
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (precioTexto.isNotEmpty()) {
                Text(
                    text = precioTexto,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VerdeBodega
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            subtotal = 21.90,
            onVolver = {},
            onConfirmarPedido = { _, _, _, _ -> }
        )
    }
}
