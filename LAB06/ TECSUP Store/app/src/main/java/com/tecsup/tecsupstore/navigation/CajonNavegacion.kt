package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CajonNavegacion(
    onOpcionSeleccionada: (String) -> Unit
) {

    ModalDrawerSheet {

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "TECSUP Store",
            modifier = Modifier.padding(24.dp)
        )

        NavigationDrawerItem(
            label = {
                Text("Inicio")
            },
            selected = false,
            onClick = {
                onOpcionSeleccionada("inicio")
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Mis pedidos")
            },
            selected = false,
            onClick = {
                onOpcionSeleccionada("pedidos")
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null
                )
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Favoritos")
            },
            selected = false,
            onClick = {
                onOpcionSeleccionada("favoritos")
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Perfil")
            },
            selected = false,
            onClick = {
                onOpcionSeleccionada("perfil")
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        )
    }
}