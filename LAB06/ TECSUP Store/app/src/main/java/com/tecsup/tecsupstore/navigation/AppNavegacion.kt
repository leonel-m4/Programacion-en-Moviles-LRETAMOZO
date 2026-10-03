package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tecsup.tecsupstore.ItemCarrito
import com.tecsup.tecsupstore.Pedido
import com.tecsup.tecsupstore.Producto
import com.tecsup.tecsupstore.Usuario
import com.tecsup.tecsupstore.listaProductosControlada
import com.tecsup.tecsupstore.screens.PantallaCarrito
import com.tecsup.tecsupstore.screens.PantallaFavoritos
import com.tecsup.tecsupstore.screens.PantallaInicio
import com.tecsup.tecsupstore.screens.PantallaPedidos
import com.tecsup.tecsupstore.screens.PantallaPerfil
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val estadoDrawer = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var favoritosIds by remember { mutableStateOf(setOf<Int>()) }
    var carritoItems by remember { mutableStateOf(listOf<ItemCarrito>()) }
    var pedidosList by remember { mutableStateOf(listOf<Pedido>()) }
    var usuario by remember {
        mutableStateOf(
            Usuario(
                nombre = "Maria Rojas",
                correo = "maria@tecsup.edu.pe",
                telefono = "+51 987 654 321",
                direccion = "Av. Principal 123, Lima"
            )
        )
    }

    val listaProductos = remember { listaProductosControlada }
    val totalCarritoCount = carritoItems.sumOf { it.cantidad }

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route ?: Pantalla.Inicio.ruta

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {
            CajonNavegacion(
                rutaActual = rutaActual,
                usuario = usuario,
                cantidadFavoritos = favoritosIds.size,
                cantidadCarrito = totalCarritoCount,
                cantidadPedidos = pedidosList.size,
                isDarkMode = isDarkMode,
                onToggleDarkMode = onToggleDarkMode,
                onOpcionSeleccionada = { ruta ->
                    navController.navigate(ruta) {
                        popUpTo(Pantalla.Inicio.ruta)
                        launchSingleTop = true
                    }
                    scope.launch { estadoDrawer.close() }
                }
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { estadoDrawer.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    title = {
                        Column {
                            Text(
                                text = "TECSUP Store",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp
                            )
                            Text(
                                text = when (rutaActual) {
                                    Pantalla.Inicio.ruta -> "Catálogo principal"
                                    Pantalla.Pedidos.ruta -> "Mis pedidos"
                                    Pantalla.Favoritos.ruta -> "Favoritos"
                                    Pantalla.Carrito.ruta -> "Carrito de compras"
                                    Pantalla.Perfil.ruta -> "Perfil"
                                    else -> "Tienda oficial"
                                },
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Pantalla.Inicio.ruta,
                modifier = Modifier.padding(padding)
            ) {
                composable(route = Pantalla.Inicio.ruta) {
                    PantallaInicio(
                        productos = listaProductos,
                        favoritosIds = favoritosIds,
                        onToggleFavorito = { id ->
                            val esFavorito = id in favoritosIds
                            favoritosIds = if (esFavorito) favoritosIds - id else favoritosIds + id
                            val prod = listaProductos.find { it.id == id }
                            scope.launch {
                                val mensaje = if (esFavorito) "Quitado de favoritos: ${prod?.nombre}" else "Agregado a favoritos: ${prod?.nombre}"
                                snackbarHostState.showSnackbar(mensaje)
                            }
                        },
                        onAgregarCarrito = { producto ->
                            val index = carritoItems.indexOfFirst { it.producto.id == producto.id }
                            carritoItems = if (index >= 0) {
                                carritoItems.mapIndexed { idx, item ->
                                    if (idx == index) item.copy(cantidad = item.cantidad + 1) else item
                                }
                            } else {
                                carritoItems + ItemCarrito(producto, 1)
                            }
                            scope.launch {
                                snackbarHostState.showSnackbar("Agregado al carrito: ${producto.nombre}")
                            }
                        }
                    )
                }

                composable(route = Pantalla.Pedidos.ruta) {
                    PantallaPedidos(pedidos = pedidosList)
                }

                composable(route = Pantalla.Favoritos.ruta) {
                    val productosFavoritos = listaProductos.filter { it.id in favoritosIds }
                    PantallaFavoritos(
                        productosFavoritos = productosFavoritos,
                        favoritosIds = favoritosIds,
                        onToggleFavorito = { id ->
                            favoritosIds = favoritosIds - id
                            scope.launch {
                                snackbarHostState.showSnackbar("Producto eliminado de favoritos")
                            }
                        },
                        onAgregarCarrito = { producto ->
                            val index = carritoItems.indexOfFirst { it.producto.id == producto.id }
                            carritoItems = if (index >= 0) {
                                carritoItems.mapIndexed { idx, item ->
                                    if (idx == index) item.copy(cantidad = item.cantidad + 1) else item
                                }
                            } else {
                                carritoItems + ItemCarrito(producto, 1)
                            }
                            scope.launch {
                                snackbarHostState.showSnackbar("Agregado al carrito: ${producto.nombre}")
                            }
                        }
                    )
                }

                composable(route = Pantalla.Carrito.ruta) {
                    PantallaCarrito(
                        carritoItems = carritoItems,
                        onIncrementar = { item ->
                            carritoItems = carritoItems.map {
                                if (it.producto.id == item.producto.id) it.copy(cantidad = it.cantidad + 1) else it
                            }
                        },
                        onDecrementar = { item ->
                            carritoItems = carritoItems.mapNotNull {
                                if (it.producto.id == item.producto.id) {
                                    if (it.cantidad > 1) it.copy(cantidad = it.cantidad - 1) else null
                                } else it
                            }
                        },
                        onEliminarItem = { item ->
                            carritoItems = carritoItems.filter { it.producto.id != item.producto.id }
                            scope.launch {
                                snackbarHostState.showSnackbar("Eliminado del carrito: ${item.producto.nombre}")
                            }
                        },
                        onCheckout = {
                            if (carritoItems.isNotEmpty()) {
                                val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
                                val totalCompra = carritoItems.sumOf { it.producto.precio * it.cantidad } + 15.00
                                val nuevoPedido = Pedido(
                                    codigo = "PED-2026-${(100..999).random()}",
                                    fecha = fechaActual,
                                    items = carritoItems,
                                    total = totalCompra,
                                    estado = "En preparación"
                                )
                                pedidosList = listOf(nuevoPedido) + pedidosList
                                carritoItems = emptyList()
                                scope.launch {
                                    snackbarHostState.showSnackbar("¡Compra realizada con éxito! Pedido generado.")
                                }
                                navController.navigate(Pantalla.Pedidos.ruta) {
                                    popUpTo(Pantalla.Inicio.ruta)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }

                composable(route = Pantalla.Perfil.ruta) {
                    PantallaPerfil(
                        usuario = usuario,
                        onActualizarUsuario = { nuevoUsuario ->
                            usuario = nuevoUsuario
                            scope.launch {
                                snackbarHostState.showSnackbar("Perfil actualizado correctamente")
                            }
                        }
                    )
                }
            }
        }
    }
}
