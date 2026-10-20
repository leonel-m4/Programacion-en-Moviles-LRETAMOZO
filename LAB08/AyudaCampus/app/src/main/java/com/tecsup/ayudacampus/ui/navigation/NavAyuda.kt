package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.tecsup.ayudacampus.ui.cliente.PerfilScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.soporte.AjustesScreen
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

private data class ItemNav(val ruta: String, val titulo: String, val icono: ImageVector)

private val itemsCliente = listOf(
    ItemNav(Rutas.MIS_TICKETS, "Tickets", Icons.Filled.List),
    ItemNav(Rutas.NUEVO_TICKET, "Nuevo", Icons.Filled.Add),
    ItemNav(Rutas.PERFIL, "Perfil", Icons.Filled.Person)
)

private val itemsSoporte = listOf(
    ItemNav(Rutas.PANEL, "Panel", Icons.Filled.Dashboard),
    ItemNav(Rutas.BANDEJA, "Tickets", Icons.Filled.List),
    ItemNav(Rutas.CATEGORIAS, "Categorías", Icons.Filled.Category),
    ItemNav(Rutas.AJUSTES, "Ajustes", Icons.Filled.Settings)
)

private fun irA(nav: NavController, ruta: String) {
    nav.navigate(ruta) {
        popUpTo(nav.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private fun cambiarGrafo(nav: NavController, ruta: String) {
    nav.navigate(ruta) {
        popUpTo(nav.graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
private fun Barra(items: List<ItemNav>, nav: NavController) {
    val entrada by nav.currentBackStackEntryAsState()
    val actual = entrada?.destination?.route
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                selected = actual == it.ruta,
                onClick = { irA(nav, it.ruta) },
                icon = { Icon(it.icono, contentDescription = it.titulo) },
                label = { Text(it.titulo) }
            )
        }
    }
}

@Composable
fun NavAyuda() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as AyudaApp
    val prefs = remember { PreferenciasStore(app) }
    val factory = remember { AyudaViewModelFactory(app.repository, prefs) }
    val sesion: SesionViewModel = viewModel(factory = factory)

    val oscuro by sesion.modoOscuro.collectAsState()
    val recibirTickets by sesion.recibirTickets.collectAsState()
    val usuarioId by sesion.usuarioId.collectAsState()

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) {
            AyudaTheme(Rol.CLIENTE, oscuro) {
                SplashScreen(
                    onDestino = { ruta ->
                        navController.navigate(ruta) {
                            popUpTo(Rutas.SPLASH) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                vm = sesion,
                onLoginExitoso = { rol ->
                    val destino = if (rol == Rol.ADMIN.name) Rutas.PANEL else Rutas.MIS_TICKETS
                    navController.navigate(destino) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.MIS_TICKETS) {
            if (usuarioId > 0) {
                val vm: MisTicketsViewModel = viewModel(
                    key = "mis_tickets_$usuarioId",
                    factory = viewModelFactory {
                        initializer { MisTicketsViewModel(app.repository, usuarioId) }
                    }
                )
                AyudaTheme(Rol.CLIENTE, oscuro) {
                    Scaffold(bottomBar = { Barra(itemsCliente, navController) }) { p ->
                        Box(Modifier.padding(p)) {
                            MisTicketsScreen(
                                vm = vm,
                                recibirTickets = recibirTickets,
                                onTicket = { navController.navigate("${Rutas.DETALLE}/$it") },
                                onNuevo = { irA(navController, Rutas.NUEVO_TICKET) }
                            )
                        }
                    }
                }
            }
        }

        composable(Rutas.NUEVO_TICKET) {
            if (usuarioId > 0) {
                val vm: NuevoTicketViewModel = viewModel(
                    key = "nuevo_ticket_$usuarioId",
                    factory = viewModelFactory {
                        initializer { NuevoTicketViewModel(app.repository, usuarioId) }
                    }
                )
                AyudaTheme(Rol.CLIENTE, oscuro) {
                    Scaffold(bottomBar = { Barra(itemsCliente, navController) }) { p ->
                        Box(Modifier.padding(p)) {
                            NuevoTicketScreen(
                                vm = vm,
                                recibirTickets = recibirTickets,
                                onCreado = { irA(navController, Rutas.MIS_TICKETS) }
                            )
                        }
                    }
                }
            }
        }

        composable(Rutas.PERFIL) {
            if (usuarioId > 0) {
                val misTickets: MisTicketsViewModel = viewModel(
                    key = "perfil_tickets_$usuarioId",
                    factory = viewModelFactory {
                        initializer { MisTicketsViewModel(app.repository, usuarioId) }
                    }
                )
                AyudaTheme(Rol.CLIENTE, oscuro) {
                    Scaffold(bottomBar = { Barra(itemsCliente, navController) }) { p ->
                        Box(Modifier.padding(p)) {
                            PerfilScreen(
                                sesion = sesion,
                                misTickets = misTickets,
                                onCambiarPerfil = {
                                    sesion.cambiarPerfil(Rol.CLIENTE) {
                                        cambiarGrafo(navController, Rutas.PANEL)
                                    }
                                },
                                onCerrarSesion = {
                                    sesion.cerrarSesion { cambiarGrafo(navController, Rutas.LOGIN) }
                                }
                            )
                        }
                    }
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
            AyudaTheme(Rol.CLIENTE, oscuro) {
                DetalleTicketScreen(vm = vm)
            }
        }

        composable(Rutas.PANEL) {
            val vm: PanelViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { PanelViewModel(app.repository) }
                }
            )
            AyudaTheme(Rol.ADMIN, oscuro) {
                Scaffold(bottomBar = { Barra(itemsSoporte, navController) }) { p ->
                    Box(Modifier.padding(p)) {
                        PanelScreen(
                            vm = vm,
                            onVerAltas = { irA(navController, Rutas.BANDEJA) }
                        )
                    }
                }
            }
        }

        composable(Rutas.BANDEJA) {
            val vm: BandejaViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { BandejaViewModel(app.repository) }
                }
            )
            AyudaTheme(Rol.ADMIN, oscuro) {
                Scaffold(bottomBar = { Barra(itemsSoporte, navController) }) { p ->
                    Box(Modifier.padding(p)) {
                        BandejaScreen(
                            vm = vm,
                            onTicket = { navController.navigate("${Rutas.ATENDER}/$it") }
                        )
                    }
                }
            }
        }

        composable(
            route = Rutas.ATENDER_RUTA,
            arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
        ) { entry ->
            val ticketId = entry.arguments?.getInt("ticketId") ?: return@composable
            if (usuarioId > 0) {
                val vm: AtenderViewModel = viewModel(
                    key = "atender_$ticketId",
                    factory = viewModelFactory {
                        initializer {
                            AtenderViewModel(
                                repo = app.repository,
                                ticketId = ticketId,
                                autor = Usuario(
                                    id = usuarioId,
                                    nombre = "Soporte TI",
                                    correo = "",
                                    clave = "",
                                    rol = Rol.ADMIN.name
                                )
                            )
                        }
                    }
                )
                AyudaTheme(Rol.ADMIN, oscuro) {
                    AtenderTicketScreen(
                        vm = vm,
                        onGuardado = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(Rutas.CATEGORIAS) {
            val vm: CategoriasViewModel = viewModel(factory = factory)
            AyudaTheme(Rol.ADMIN, oscuro) {
                Scaffold(bottomBar = { Barra(itemsSoporte, navController) }) { p ->
                    Box(Modifier.padding(p)) {
                        CategoriasScreen(
                            vm = vm,
                            onEditar = { navController.navigate("${Rutas.FORM_CATEGORIA}/$it") },
                            onNueva = { navController.navigate("${Rutas.FORM_CATEGORIA}/-1") }
                        )
                    }
                }
            }
        }

        composable(
            route = Rutas.FORM_CATEGORIA_RUTA,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: -1
            val vm: CategoriasViewModel = viewModel(key = "form_categoria", factory = factory)
            AyudaTheme(Rol.ADMIN, oscuro) {
                FormularioCategoriaScreen(
                    vm = vm,
                    categoriaId = if (id == -1) null else id,
                    onListo = { navController.popBackStack() }
                )
            }
        }

        composable(Rutas.AJUSTES) {
            AyudaTheme(Rol.ADMIN, oscuro) {
                Scaffold(bottomBar = { Barra(itemsSoporte, navController) }) { p ->
                    Box(Modifier.padding(p)) {
                        AjustesScreen(
                            sesion = sesion,
                            onCambiarPerfil = {
                                sesion.cambiarPerfil(Rol.ADMIN) {
                                    cambiarGrafo(navController, Rutas.MIS_TICKETS)
                                }
                            },
                            onCerrarSesion = {
                                sesion.cerrarSesion { cambiarGrafo(navController, Rutas.LOGIN) }
                            }
                        )
                    }
                }
            }
        }
    }
}