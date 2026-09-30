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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Morado = Color(0xFF6A2C91)
private val LilaSeleccionado = Color(0xFFEADCF3)

@Composable
fun CajonNavegacion(
    rutaActual: String,
    onOpcionSeleccionada: (String) -> Unit
) {

    ModalDrawerSheet(
        drawerContainerColor = Color.White
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
                        .size(54.dp)
                        .background(
                            color = Color(0xFFF0E4F6),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "MR",
                        color = Morado,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        start = 14.dp
                    )
                ) {

                    Text(
                        text = "Maria Rojas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "maria@tecsup.edu.pe",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            HorizontalDivider()
        }

        NavigationDrawerItem(
            label = {
                Text("Inicio")
            },
            selected = rutaActual == Pantalla.Inicio.ruta,
            onClick = {
                onOpcionSeleccionada(
                    Pantalla.Inicio.ruta
                )
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            colors = coloresDrawer(),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 3.dp
            )
        )

        NavigationDrawerItem(
            label = {
                Text("Mis pedidos")
            },
            selected = rutaActual == Pantalla.Pedidos.ruta,
            onClick = {
                onOpcionSeleccionada(
                    Pantalla.Pedidos.ruta
                )
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null
                )
            },
            colors = coloresDrawer(),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 3.dp
            )
        )

        NavigationDrawerItem(
            label = {
                Text("Favoritos")
            },
            selected = rutaActual == Pantalla.Favoritos.ruta,
            onClick = {
                onOpcionSeleccionada(
                    Pantalla.Favoritos.ruta
                )
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            },
            colors = coloresDrawer(),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 3.dp
            )
        )

        NavigationDrawerItem(
            label = {
                Text("Perfil")
            },
            selected = rutaActual == Pantalla.Perfil.ruta,
            onClick = {
                onOpcionSeleccionada(
                    Pantalla.Perfil.ruta
                )
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            colors = coloresDrawer(),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 3.dp
            )
        )

        NavigationDrawerItem(
            label = {
                Text("Cerrar sesión")
            },
            selected = false,
            onClick = {},
            icon = {

                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null
                )
            },
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 3.dp
            )
        )
    }
}

@Composable
private fun coloresDrawer() =
    NavigationDrawerItemDefaults.colors(
        selectedContainerColor = LilaSeleccionado,
        selectedIconColor = Morado,
        selectedTextColor = Morado
    )