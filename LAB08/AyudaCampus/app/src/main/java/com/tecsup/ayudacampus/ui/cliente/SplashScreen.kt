package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.navigation.Rutas
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(onDestino: (String) -> Unit) {
    val prefs = PreferenciasStore(LocalContext.current)

    LaunchedEffect(Unit) {
        val ruta = when (prefs.perfilActivo.first()) {
            Rol.CLIENTE.name -> Rutas.MIS_TICKETS
            Rol.ADMIN.name -> Rutas.PANEL
            else -> Rutas.LOGIN
        }
        onDestino(ruta)
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}