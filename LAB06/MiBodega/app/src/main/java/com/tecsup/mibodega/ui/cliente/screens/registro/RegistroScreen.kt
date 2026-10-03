package com.tecsup.mibodega.ui.cliente.screens.registro

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
 * Pantalla 2: Registro de datos rediseñada.
 */
@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onCrearCuenta: (nombre: String, telefono: String, direccion: String, referencia: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var mostrarErrores by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Ingresa tus datos para realizar tus pedidos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto de perfil",
                    tint = VerdeBodega,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Nombre completo",
            valor = nombre,
            iconoLeading = Icons.Default.Person,
            onValorCambia = { nombre = it },
            placeholder = "Juan Pérez",
            esError = mostrarErrores && nombre.isBlank()
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            iconoLeading = Icons.Default.Phone,
            onValorCambia = { telefono = it },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone,
            esError = mostrarErrores && telefono.isBlank()
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Dirección de entrega",
            valor = direccion,
            iconoLeading = Icons.Default.Home,
            onValorCambia = { direccion = it },
            placeholder = "Av. Los Olivos 123",
            esError = mostrarErrores && direccion.isBlank()
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Referencia de entrega",
            valor = referencia,
            iconoLeading = Icons.Default.PinDrop,
            onValorCambia = { referencia = it },
            placeholder = "Frente al parque principal",
            esError = mostrarErrores && referencia.isBlank()
        )

        AnimatedVisibility(visible = mostrarErrores && listOf(nombre, telefono, direccion, referencia).any { it.isBlank() }) {
            Column {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Por favor, completa todos los campos requeridos.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Crear mi Cuenta",
            onClick = {
                mostrarErrores = true
                if (listOf(nombre, telefono, direccion, referencia).all { it.isNotBlank() }) {
                    onCrearCuenta(nombre, telefono, direccion, referencia)
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroPreview() {
    BodegaTheme {
        RegistroScreen(onVolver = {}, onCrearCuenta = { _, _, _, _ -> })
    }
}
