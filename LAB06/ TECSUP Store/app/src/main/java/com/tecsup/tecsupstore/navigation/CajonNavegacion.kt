package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.tecsupstore.Usuario

@Composable
fun CajonNavegacion(
    rutaActual: String,
    usuario: Usuario,
    cantidadFavoritos: Int = 0,
    cantidadCarrito: Int = 0,
    cantidadPedidos: Int = 0,
    isDarkMode: Boolean = false,
    onToggleDarkMode: (Boolean) -> Unit,
    onOpcionSeleccionada: (String) -> Unit
) {
    val iniciales = usuario.nombre.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (iniciales.isNotEmpty()) iniciales else "MR",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 14.dp)
                ) {
                    Text(
                        text = usuario.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = usuario.correo,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        }

        NavigationDrawerItem(
            label = { Text("Inicio", fontWeight = FontWeight.Medium) },
            selected = rutaActual == Pantalla.Inicio.ruta,
            onClick = { onOpcionSeleccionada(Pantalla.Inicio.ruta) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Mis pedidos", fontWeight = FontWeight.Medium) },
            selected = rutaActual == Pantalla.Pedidos.ruta,
            onClick = { onOpcionSeleccionada(Pantalla.Pedidos.ruta) },
            icon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) },
            badge = {
                if (cantidadPedidos > 0) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Text(text = cantidadPedidos.toString())
                    }
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Favoritos", fontWeight = FontWeight.Medium) },
            selected = rutaActual == Pantalla.Favoritos.ruta,
            onClick = { onOpcionSeleccionada(Pantalla.Favoritos.ruta) },
            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
            badge = {
                if (cantidadFavoritos > 0) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text(text = cantidadFavoritos.toString())
                    }
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Carrito", fontWeight = FontWeight.Medium) },
            selected = rutaActual == Pantalla.Carrito.ruta,
            onClick = { onOpcionSeleccionada(Pantalla.Carrito.ruta) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            badge = {
                if (cantidadCarrito > 0) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ) {
                        Text(text = cantidadCarrito.toString())
                    }
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        NavigationDrawerItem(
            label = { Text("Perfil", fontWeight = FontWeight.Medium) },
            selected = rutaActual == Pantalla.Perfil.ruta,
            onClick = { onOpcionSeleccionada(Pantalla.Perfil.ruta) },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Dark Mode Toggle Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = if (isDarkMode) "Modo oscuro" else "Modo claro",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = onToggleDarkMode
            )
        }

        NavigationDrawerItem(
            label = { Text("Cerrar sesión", fontWeight = FontWeight.Medium) },
            selected = false,
            onClick = { /* Simulado */ },
            icon = { Icon(Icons.Default.Logout, contentDescription = null) },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.error,
                unselectedTextColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )
    }
}
