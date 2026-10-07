package com.tecsup.mibodega.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.theme.VerdeBodega

/** Las barras permanecen fuera del contenido que cambia entre secciones. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteScaffold(
    seccionSeleccionada: SeccionCliente,
    cantidadCarrito: Int,
    cantidadFavoritos: Int,
    onCambiarSeccion: (SeccionCliente) -> Unit,
    onVerCarrito: () -> Unit,
    contenido: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("barra_superior_cliente"),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = VerdeBodega,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Mi Bodega", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                            Text(
                                text = "Productos de calidad al mejor precio",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onVerCarrito,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f), CircleShape)
                    ) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    ) {
                                        Text("$cantidadCarrito", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("barra_inferior_cliente"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                SeccionCliente.entries.forEach { seccion ->
                    val icono = when (seccion) {
                        SeccionCliente.INICIO -> Icons.Default.Home
                        SeccionCliente.FAVORITOS -> Icons.Default.Favorite
                        SeccionCliente.PEDIDOS -> Icons.Default.Receipt
                        SeccionCliente.PERFIL -> Icons.Default.Person
                    }
                    val seleccionada = seccion == seccionSeleccionada
                    NavigationBarItem(
                        modifier = Modifier.testTag("nav_${seccion.name.lowercase()}"),
                        selected = seleccionada,
                        onClick = { onCambiarSeccion(seccion) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (seccion == SeccionCliente.FAVORITOS && cantidadFavoritos > 0) {
                                        Badge { Text("$cantidadFavoritos") }
                                    }
                                }
                            ) {
                                Icon(icono, contentDescription = seccion.etiqueta)
                            }
                        },
                        label = {
                            Text(
                                text = seccion.etiqueta,
                                fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeBodega,
                            selectedTextColor = VerdeBodega,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingInterno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .consumeWindowInsets(paddingInterno)
        ) {
            contenido()
        }
    }
}
