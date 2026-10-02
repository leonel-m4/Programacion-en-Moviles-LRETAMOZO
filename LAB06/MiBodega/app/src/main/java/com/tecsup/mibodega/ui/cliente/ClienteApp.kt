package com.tecsup.mibodega.ui.cliente

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.Modifier
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
 * - Tiene el NavHost con las rutas de cada pantalla.
 * - Tiene el estado del carrito (List<ItemCarrito>), que se reparte
 *   hacia abajo a Inicio, Detalle, Carrito y Entrega.
 * Ninguna Screen navega sola ni modifica el carrito directamente:
 * todas reciben funciones (lambdas) desde aquí (state hoisting).
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ClienteApp(
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val rutaActual by navController.currentBackStackEntryAsState()

    // El carrito vive aquí arriba, no en ninguna Screen.
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var nombreCliente by remember { mutableStateOf("Juan Pérez") }
    var telefonoCliente by remember { mutableStateOf("987 654 321") }
    var direccionCliente by remember { mutableStateOf("Av. Los Olivos 123") }
    var referenciaCliente by remember { mutableStateOf("Frente al parque") }
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var totalConfirmado by remember { mutableStateOf(0.00) }
    var tipoEntregaConfirmada by remember { mutableStateOf("Delivery") }
    var numeroPedidoConfirmado by remember { mutableStateOf(1) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        AnimatedContent(
            targetState = rutaActual?.destination?.route,
            modifier = Modifier.fillMaxSize(),
            label = "CambioPantalla"
        ) {
            NavHost(
                navController = navController,
                startDestination = Rutas.BIENVENIDA
            ) {
                composable(Rutas.BIENVENIDA) {
                    BienvenidaScreen(
                        onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                        onIniciarSesion = { navController.navigate(Rutas.LOGIN) },
                        onTerminos = { /* TODO: abrir términos y condiciones */ }
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
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                favoritos = favoritos,
                onVerCarrito = { navegarSinDuplicar(navController, Rutas.CARRITO) },
                onVerPedidos = { navegarSinDuplicar(navController, Rutas.PEDIDOS) },
                onVerFavoritos = { navegarSinDuplicar(navController, Rutas.FAVORITOS) },
                onVerPerfil = { navegarSinDuplicar(navController, Rutas.PERFIL) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onFavoritoClick = { producto ->
                    favoritos = cambiarFavorito(favoritos, producto.id)
                }
            )
        }

        composable(Rutas.PEDIDOS) {
            PedidosScreen(
                pedidos = pedidos,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.FAVORITOS) {
            FavoritosScreen(
                productos = listaProductosFake.filter { favoritos.contains(it.id) },
                onVolver = { navController.popBackStack() },
                onProductoClick = { producto -> navController.navigate(Rutas.detalle(producto.id)) },
                onAgregarProducto = { producto -> carrito = agregarOSumarProducto(carrito, producto, 1) },
                onFavoritoClick = { producto -> favoritos = cambiarFavorito(favoritos, producto.id) }
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                nombre = nombreCliente,
                telefono = telefonoCliente,
                direccion = direccionCliente,
                modoOscuro = modoOscuro,
                onCambiarModoOscuro = onCambiarModoOscuro,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.navigate(Rutas.CARRITO)
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
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
                            else -> null // si llega a 0, se elimina de la lista
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = { navegarSinDuplicar(navController, Rutas.ENTREGA) }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                subtotal = carrito.sumOf { it.producto.precio * it.cantidad },
                onVolver = { navController.popBackStack() },
                onConfirmarPedido = { tipoEntrega, costoEntrega ->
                    val nuevoNumero = pedidos.size + 1
                    val nuevoTotal = carrito.sumOf { it.producto.precio * it.cantidad } + costoEntrega
                    tipoEntregaConfirmada = tipoEntrega
                    totalConfirmado = nuevoTotal
                    numeroPedidoConfirmado = nuevoNumero
                    pedidos = pedidos + Pedido(
                        numero = nuevoNumero,
                        total = nuevoTotal,
                        tipoEntrega = tipoEntrega,
                        direccion = direccionCliente
                    )
                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.ENTREGA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                total = totalConfirmado,
                tipoEntrega = tipoEntregaConfirmada,
                numeroPedido = numeroPedidoConfirmado,
                onVolverInicio = {
                    carrito = emptyList()
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                    }
                }
            )
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
