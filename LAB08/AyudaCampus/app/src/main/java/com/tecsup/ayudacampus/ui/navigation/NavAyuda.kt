package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.ayudacampus.AyudaApp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.DetalleTicketScreen
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.NuevoTicketScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.soporte.AtenderTicketScreen
import com.tecsup.ayudacampus.ui.soporte.BandejaScreen
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.ui.soporte.FormularioCategoriaScreen
import com.tecsup.ayudacampus.ui.soporte.PanelScreen
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel
import com.tecsup.ayudacampus.viewmodel.PanelViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel

@Composable
fun NavAyuda() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as AyudaApp
    val prefs = remember { PreferenciasStore(app) }
    val factory = remember { AyudaViewModelFactory(app.repository, prefs) }

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) {
            SplashScreen(
                onDestino = { ruta ->
                    navController.navigate(ruta) {
                        popUpTo(Rutas.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            val vm: SesionViewModel = viewModel(factory = factory)
            LoginScreen(
                vm = vm,
                onLoginExitoso = { rol ->
                    val destino = if (rol == Rol.ADMIN.name) Rutas.PANEL else Rutas.MIS_TICKETS
                    navController.navigate(destino) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.MIS_TICKETS) {
            val clienteId by prefs.usuarioId.collectAsState(initial = 0)
            if (clienteId > 0) {
                val vm: MisTicketsViewModel = viewModel(
                    key = "mis_tickets_$clienteId",
                    factory = viewModelFactory {
                        initializer { MisTicketsViewModel(app.repository, clienteId) }
                    }
                )
                AyudaTheme(Rol.CLIENTE) {
                    MisTicketsScreen(
                        vm = vm,
                        onTicket = { navController.navigate("${Rutas.DETALLE}/$it") },
                        onNuevo = { navController.navigate(Rutas.NUEVO_TICKET) }
                    )
                }
            }
        }

        composable(Rutas.NUEVO_TICKET) {
            val clienteId by prefs.usuarioId.collectAsState(initial = 0)
            if (clienteId > 0) {
                val vm: NuevoTicketViewModel = viewModel(
                    key = "nuevo_ticket_$clienteId",
                    factory = viewModelFactory {
                        initializer { NuevoTicketViewModel(app.repository, clienteId) }
                    }
                )
                AyudaTheme(Rol.CLIENTE) {
                    NuevoTicketScreen(
                        vm = vm,
                        onCreado = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(
            route = Rutas.DETALLE_RUTA,
            arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
        ) { entry ->
            val ticketId = entry.arguments?.getInt("ticketId") ?: return@composable
            val vm: TicketDetalleViewModel = viewModel(
                key = "detalle_$ticketId",
                factory = viewModelFactory {
                    initializer { TicketDetalleViewModel(app.repository, ticketId) }
                }
            )
            AyudaTheme(Rol.CLIENTE) {
                DetalleTicketScreen(vm = vm)
            }
        }

        composable(Rutas.PANEL) {
            val vm: PanelViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { PanelViewModel(app.repository) }
                }
            )
            AyudaTheme(Rol.ADMIN) {
                PanelScreen(
                    vm = vm,
                    onVerAltas = { navController.navigate(Rutas.BANDEJA) },
                    onCategorias = { navController.navigate(Rutas.CATEGORIAS) }
                )
            }
        }

        composable(Rutas.BANDEJA) {
            val vm: BandejaViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { BandejaViewModel(app.repository) }
                }
            )
            AyudaTheme(Rol.ADMIN) {
                BandejaScreen(
                    vm = vm,
                    onTicket = { navController.navigate("${Rutas.ATENDER}/$it") }
                )
            }
        }

        composable(
            route = Rutas.ATENDER_RUTA,
            arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
        ) { entry ->
            val ticketId = entry.arguments?.getInt("ticketId") ?: return@composable
            val soporteId by prefs.usuarioId.collectAsState(initial = 0)
            if (soporteId > 0) {
                val vm: AtenderViewModel = viewModel(
                    key = "atender_$ticketId",
                    factory = viewModelFactory {
                        initializer {
                            AtenderViewModel(
                                repo = app.repository,
                                ticketId = ticketId,
                                autor = Usuario(
                                    id = soporteId,
                                    nombre = "Soporte TI",
                                    correo = "",
                                    clave = "",
                                    rol = Rol.ADMIN.name
                                )
                            )
                        }
                    }
                )
                AyudaTheme(Rol.ADMIN) {
                    AtenderTicketScreen(
                        vm = vm,
                        onGuardado = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(Rutas.CATEGORIAS) {
            val vm: CategoriasViewModel = viewModel(factory = factory)
            AyudaTheme(Rol.ADMIN) {
                CategoriasScreen(
                    vm = vm,
                    onEditar = { navController.navigate("${Rutas.FORM_CATEGORIA}/$it") },
                    onNueva = { navController.navigate("${Rutas.FORM_CATEGORIA}/-1") }
                )
            }
        }

        composable(
            route = Rutas.FORM_CATEGORIA_RUTA,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: -1
            val vm: CategoriasViewModel = viewModel(key = "form_categoria", factory = factory)
            AyudaTheme(Rol.ADMIN) {
                FormularioCategoriaScreen(
                    vm = vm,
                    categoriaId = if (id == -1) null else id,
                    onListo = { navController.popBackStack() }
                )
            }
        }
    }
}