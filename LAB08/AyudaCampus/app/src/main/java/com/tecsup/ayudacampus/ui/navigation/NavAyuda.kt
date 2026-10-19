package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.ayudacampus.AyudaApp
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun NavAyuda() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as AyudaApp
    val factory = AyudaViewModelFactory(app.repository, PreferenciasStore(app))

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

        composable(Rutas.MIS_TICKETS) { Text("Mis tickets") }

        composable(Rutas.PANEL) { Text("Panel") }
    }
}