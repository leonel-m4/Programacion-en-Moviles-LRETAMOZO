package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.tecsupstore.screens.PantallaInicio
import kotlinx.coroutines.launch

private val Morado = Color(0xFF6A2C91)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {

    val navController = rememberNavController()

    val estadoDrawer = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {

            CajonNavegacion(
                onOpcionSeleccionada = { ruta ->

                    navController.navigate(ruta) {
                        launchSingleTop = true
                    }

                    scope.launch {
                        estadoDrawer.close()
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {

                TopAppBar(
                    navigationIcon = {

                        IconButton(
                            onClick = {

                                scope.launch {
                                    estadoDrawer.open()
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },

                    title = {

                        Column {

                            Text(
                                text = "TECSUP Store",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp
                            )

                            Text(
                                text = "Mas vendidos",
                                color = Color.White.copy(
                                    alpha = 0.8f
                                ),
                                fontSize = 11.sp
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Morado
                    )
                )
            }
        ) { padding ->

            NavHost(
                navController = navController,
                startDestination = Pantalla.Inicio.ruta,
                modifier = Modifier.padding(padding)
            ) {

                composable(
                    route = Pantalla.Inicio.ruta
                ) {

                    PantallaInicio()
                }

                composable(
                    route = Pantalla.Pedidos.ruta
                ) {

                    PantallaDestino(
                        titulo = "Mis pedidos"
                    )
                }

                composable(
                    route = Pantalla.Favoritos.ruta
                ) {

                    PantallaDestino(
                        titulo = "Favoritos"
                    )
                }

                composable(
                    route = Pantalla.Perfil.ruta
                ) {

                    PantallaDestino(
                        titulo = "Perfil"
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaDestino(
    titulo: String
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = titulo
        )
    }
}