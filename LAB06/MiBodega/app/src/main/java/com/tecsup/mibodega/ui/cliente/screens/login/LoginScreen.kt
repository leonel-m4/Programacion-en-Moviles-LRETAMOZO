package com.tecsup.mibodega.ui.cliente.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun LoginScreen(
    onVolver: () -> Unit,
    onLoginCorrecto: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
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

        Spacer(Modifier.height(28.dp))

        Text(
            text = "¡Hola de nuevo!",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Ingresa tus credenciales para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(20.dp))

        // Badge de credenciales demo
        Card(
            shape = RoundedCornerShape(14.dp),
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
                        text = "Credenciales Demo",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Usuario: cliente  |  Contraseña: 1234",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Usuario",
            valor = usuario,
            iconoLeading = Icons.Default.Person,
            onValorCambia = {
                usuario = it
                error = false
            },
            placeholder = "cliente",
            esError = error
        )

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = clave,
            iconoLeading = Icons.Default.Lock,
            onValorCambia = {
                clave = it
                error = false
            },
            placeholder = "1234",
            teclado = KeyboardType.Number,
            esPassword = true,
            esError = error
        )

        AnimatedVisibility(visible = error) {
            Column {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Usuario o contraseña incorrectos. Usa cliente / 1234",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        BotonPrimario(
            texto = "Ingresar a Mi Bodega",
            onClick = {
                if (usuario.trim() == "cliente" && clave.trim() == "1234") {
                    onLoginCorrecto()
                } else {
                    error = true
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    BodegaTheme {
        LoginScreen({}, {})
    }
}
