package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.Producto

@Composable
fun TarjetaProducto(
    producto: Producto
) {

    var expandido by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "S/ %.2f".format(producto.precio)
                )
            }

            Box {

                IconButton(
                    onClick = {
                        expandido = true
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones"
                    )
                }

                DropdownMenu(
                    expanded = expandido,
                    onDismissRequest = {
                        expandido = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("Favoritos")
                        },
                        onClick = {
                            expandido = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Compartir")
                        },
                        onClick = {
                            expandido = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Reportar")
                        },
                        onClick = {
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}