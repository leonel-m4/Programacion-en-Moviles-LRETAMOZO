package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.ayudacampus.AyudaApp
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.NuevoTicketScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

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
                MisTicketsScreen(
                    vm = vm,
                    onTicket = { navController.navigate("${Rutas.DETALLE}/$it") },
                    onNuevo = { navController.navigate(Rutas.NUEVO_TICKET) }
                )
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
                NuevoTicketScreen(
                    vm = vm,
                    onCreado = { navController.popBackStack() }
                )
            }
        }

        composable(Rutas.PANEL) { Text("Panel") }
    }
}