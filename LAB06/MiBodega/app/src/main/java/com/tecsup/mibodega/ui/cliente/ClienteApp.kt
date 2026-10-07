package com.tecsup.mibodega.ui.cliente

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen

/**
 * "Director de orquesta" de la app cliente:
 * - Separa el acceso a la app de su contenedor con barras fijas.
 * - Cambia las secciones por estado, sin agregar pantallas al historial.
 * - Limita la navegación de detalle y compra al cuerpo del contenedor.
 * - Mantiene el estado global del carrito, usuario registrado, favoritos y pedidos.
 * - Implementa State Hoisting centralizado.
 */
@Composable
fun ClienteApp(
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    // Estados Globales (inicialmente vacíos hasta que el usuario se registre o ingrese sus datos)
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var nombreCliente by remember { mutableStateOf("") }
    var telefonoCliente by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var referenciaCliente by remember { mutableStateOf("") }
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var totalConfirmado by remember { mutableDoubleStateOf(0.00) }
    var tipoEntregaConfirmada by remember { mutableStateOf("Delivery") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        NavHost(
            navController = navController,
            startDestination = Rutas.BIENVENIDA,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Rutas.BIENVENIDA) {
                BienvenidaScreen(
                    onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                    onIniciarSesion = { navController.navigate(Rutas.LOGIN) }
                )
            }

            composable(Rutas.LOGIN) {
                LoginScreen(
                    onVolver = { navController.popBackStack() },
                    onLoginCorrecto = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.REGISTRO) {
                RegistroScreen(
                    onVolver = { navController.popBackStack() },
                    onCrearCuenta = { nombre, telefono, direccion, referencia ->
                        nombreCliente = nombre
                        telefonoCliente = telefono
                        direccionCliente = direccion
                        referenciaCliente = referencia
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.INICIO) {
                val contenidoNavController = rememberNavController()
                var seccionSeleccionada by rememberSaveable { mutableStateOf(SeccionCliente.INICIO) }
                val estadoSecciones = rememberSaveableStateHolder()

                ClienteScaffold(
                    seccionSeleccionada = seccionSeleccionada,
                    cantidadCarrito = carrito.sumOf { it.cantidad },
                    cantidadFavoritos = favoritos.size,
                    onCambiarSeccion = { seccion ->
                        seccionSeleccionada = seccion
                        contenidoNavController.popBackStack(Rutas.SECCIONES, inclusive = false)
                    },
                    onVerCarrito = { navegarSinDuplicar(contenidoNavController, Rutas.CARRITO) }
                ) {
                    NavHost(
                        navController = contenidoNavController,
                        startDestination = Rutas.SECCIONES,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Rutas.SECCIONES) {
                            BackHandler(enabled = seccionSeleccionada != SeccionCliente.INICIO) {
                                seccionSeleccionada = SeccionCliente.INICIO
                            }
                            estadoSecciones.SaveableStateProvider(seccionSeleccionada.name) {
                                when (seccionSeleccionada) {
                                    SeccionCliente.INICIO -> InicioScreen(
                                        favoritos = favoritos,
                                        onProductoClick = { producto ->
                                            contenidoNavController.navigate(Rutas.detalle(producto.id))
                                        },
                                        onAgregarProducto = { producto ->
                                            carrito = agregarOSumarProducto(carrito, producto, 1)
                                        },
                                        onFavoritoClick = { producto ->
                                            favoritos = cambiarFavorito(favoritos, producto.id)
                                        }
                                    )
                                    SeccionCliente.FAVORITOS -> FavoritosScreen(
                                        productos = listaProductosFake.filter { favoritos.contains(it.id) },
                                        onProductoClick = { producto ->
                                            contenidoNavController.navigate(Rutas.detalle(producto.id))
                                        },
                                        onAgregarProducto = { producto ->
                                            carrito = agregarOSumarProducto(carrito, producto, 1)
                                        },
                                        onFavoritoClick = { producto ->
                                            favoritos = cambiarFavorito(favoritos, producto.id)
                                        }
                                    )
                                    SeccionCliente.PEDIDOS -> PedidosScreen(pedidos = pedidos)
                                    SeccionCliente.PERFIL -> PerfilScreen(
                                        nombre = nombreCliente,
                                        telefono = telefonoCliente,
                                        direccion = direccionCliente,
                                        modoOscuro = modoOscuro,
                                        onCambiarModoOscuro = onCambiarModoOscuro
                                    )
                                }
                            }
                        }

                        composable(
                            route = Rutas.DETALLE,
                            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
                            val producto = listaProductosFake.firstOrNull { it.id == productoId }

                            if (producto != null) {
                                DetalleProductoScreen(
                                    producto = producto,
                                    esFavorito = favoritos.contains(producto.id),
                                    onVolver = { contenidoNavController.popBackStack() },
                                    onFavoritoClick = { favoritos = cambiarFavorito(favoritos, producto.id) },
                                    onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                                        carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                                        navegarSinDuplicar(contenidoNavController, Rutas.CARRITO)
                                    }
                                )
                            } else {
                                LaunchedEffect(productoId) {
                                    contenidoNavController.popBackStack()
                                }
                            }
                        }

                        composable(Rutas.CARRITO) {
                            CarritoScreen(
                                carrito = carrito,
                                onVolver = { contenidoNavController.popBackStack() },
                                onIncrementar = { producto ->
                                    carrito = carrito.map {
                                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                                    }
                                },
                                onDecrementar = { producto ->
                                    carrito = carrito.mapNotNull {
                                        when {
                                            it.producto.id != producto.id -> it
                                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                                            else -> null
                                        }
                                    }
                                },
                                onEliminar = { producto ->
                                    carrito = carrito.filterNot { it.producto.id == producto.id }
                                },
                                onContinuarPedido = { navegarSinDuplicar(contenidoNavController, Rutas.ENTREGA) },
                                onExplorarProductos = {
                                    seccionSeleccionada = SeccionCliente.INICIO
                                    contenidoNavController.popBackStack(Rutas.SECCIONES, inclusive = false)
                                }
                            )
                        }

                        composable(Rutas.ENTREGA) {
                            DatosEntregaScreen(
                                subtotal = carrito.sumOf { it.producto.precio * it.cantidad },
                                nombreInicial = nombreCliente,
                                telefonoInicial = telefonoCliente,
                                direccionInicial = direccionCliente,
                                referenciaInicial = referenciaCliente,
                                onVolver = { contenidoNavController.popBackStack() },
                                onConfirmarPedido = { tipoEntrega, costoEntrega, direccionConfirmada, referenciaConfirmada ->
                                    val nuevoTotal = carrito.sumOf { it.producto.precio * it.cantidad } + costoEntrega
                                    tipoEntregaConfirmada = tipoEntrega
                                    totalConfirmado = nuevoTotal
                                    direccionCliente = direccionConfirmada
                                    referenciaCliente = referenciaConfirmada

                                    pedidos = pedidos + Pedido(
                                        numero = pedidos.size + 1,
                                        total = nuevoTotal,
                                        tipoEntrega = tipoEntrega,
                                        direccion = direccionConfirmada
                                    )
                                    // La barra permite salir de la confirmación sin pulsar "Volver al Inicio".
                                    carrito = emptyList()
                                    contenidoNavController.navigate(Rutas.CONFIRMACION) {
                                        popUpTo(Rutas.SECCIONES) { inclusive = false }
                                    }
                                }
                            )
                        }

                        composable(Rutas.CONFIRMACION) {
                            ConfirmacionScreen(
                                total = totalConfirmado,
                                tipoEntrega = tipoEntregaConfirmada,
                                direccion = direccionCliente,
                                referencia = referenciaCliente,
                                numeroPedido = pedidos.size,
                                onVolverInicio = {
                                    seccionSeleccionada = SeccionCliente.INICIO
                                    contenidoNavController.popBackStack(Rutas.SECCIONES, inclusive = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Si el producto ya está en el carrito, le suma la cantidad;
 * si no, lo agrega como un ItemCarrito nuevo.
 */
private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}

private fun navegarSinDuplicar(navController: NavHostController, ruta: String) {
    navController.navigate(ruta) {
        launchSingleTop = true
    }
}

private fun cambiarFavorito(favoritos: Set<Int>, productoId: Int): Set<Int> {
    return if (favoritos.contains(productoId)) {
        favoritos - productoId
    } else {
        favoritos + productoId
    }
}
