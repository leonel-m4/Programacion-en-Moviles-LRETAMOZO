package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun PerfilScreen(
    nombre: String,
    telefono: String,
    direccion: String,
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoPerfil(onVolver = onVolver)

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Foto de perfil",
                tint = VerdeBodega,
                modifier = Modifier
                    .size(96.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .padding(5.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = nombre,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Cliente Mi Bodega",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        DatoPerfil(
            titulo = "Teléfono",
            valor = telefono,
            icono = { Icon(Icons.Default.Phone, contentDescription = null, tint = VerdeBodega) }
        )

        Spacer(Modifier.height(12.dp))

        DatoPerfil(
            titulo = "Dirección de entrega",
            valor = direccion,
            icono = { Icon(Icons.Default.Home, contentDescription = null, tint = VerdeBodega) }
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.DarkMode, contentDescription = null, tint = VerdeBodega)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text("Modo oscuro", fontWeight = FontWeight.Bold)
                    Text(
                        text = "Cambiar apariencia de la app",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = modoOscuro,
                    onCheckedChange = onCambiarModoOscuro
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EncabezadoPerfil(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(48.dp))
    }
    Text(
        text = "Datos de tu cuenta",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DatoPerfil(
    titulo: String,
    valor: String,
    icono: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icono()
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = valor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PerfilPreview() {
    BodegaTheme {
        PerfilScreen("Juan Pérez", "987 654 321", "Av. Los Olivos 123", false, {}, {})
    }
}
