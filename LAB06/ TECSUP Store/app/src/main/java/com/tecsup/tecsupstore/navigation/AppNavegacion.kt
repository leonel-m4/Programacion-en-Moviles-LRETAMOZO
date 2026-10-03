package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tecsup.tecsupstore.Producto
import com.tecsup.tecsupstore.screens.PantallaInicio
import com.tecsup.tecsupstore.screens.TarjetaProducto
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

    var favoritosIds by remember {
        mutableStateOf(setOf<Int>())
    }

    val listaProductos = remember {
        listOf(
            Producto(id = 1, nombre = "Audífonos", precio = 89.00),
            Producto(id = 2, nombre = "Smartwatch", precio = 199.00),
            Producto(id = 3, nombre = "Funda celular", precio = 25.00)
        )
    }

    val entradaActual by
    navController.currentBackStackEntryAsState()

    val rutaActual =
        entradaActual?.destination?.route
            ?: Pantalla.Inicio.ruta

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {
            CajonNavegacion(
                rutaActual = rutaActual,
                cantidadFavoritos = favoritosIds.size,
                onOpcionSeleccionada = { ruta ->
                    navController.navigate(ruta) {
                        popUpTo(
                            Pantalla.Inicio.ruta
                        )
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
                                text = "Más vendidos",
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
                    PantallaInicio(
                        favoritosIds = favoritosIds,
                        onToggleFavorito = { id ->
                            favoritosIds = if (id in favoritosIds) {
                                favoritosIds - id
                            } else {
                                favoritosIds + id
                            }
                        }
                    )
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
                    val productosFavoritos = listaProductos.filter { it.id in favoritosIds }
                    PantallaFavoritos(
                        productosFavoritos = productosFavoritos,
                        favoritosIds = favoritosIds,
                        onToggleFavorito = { id ->
                            favoritosIds = if (id in favoritosIds) {
                                favoritosIds - id
                            } else {
                                favoritosIds + id
                            }
                        }
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
fun PantallaFavoritos(
    productosFavoritos: List<Producto>,
    favoritosIds: Set<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    if (productosFavoritos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes productos favoritos",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(
                4.dp
            )
        ) {
            items(productosFavoritos, key = { it.id }) { producto ->
                TarjetaProducto(
                    producto = producto,
                    isFavorito = producto.id in favoritosIds,
                    onToggleFavorito = {
                        onToggleFavorito(producto.id)
                    }
                )
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
            text = titulo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
