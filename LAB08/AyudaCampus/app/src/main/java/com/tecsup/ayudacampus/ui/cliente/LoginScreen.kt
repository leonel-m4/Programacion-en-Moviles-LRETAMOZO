package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun LoginScreen(
    vm: SesionViewModel,
    onLoginExitoso: (rol: String) -> Unit
) {
    var pestana by remember { mutableIntStateOf(0) }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    val error by vm.error.collectAsState()
    val usuario by vm.usuarioActual.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()

    val rol = if (pestana == 0) Rol.CLIENTE else Rol.ADMIN

    LaunchedEffect(usuario) {
        usuario?.let { onLoginExitoso(it.rol) }
    }

    AyudaTheme(rol, oscuro) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("AyudaCampus", style = MaterialTheme.typography.headlineMedium)

                TabRow(selectedTabIndex = pestana) {
                    Tab(
                        selected = pestana == 0,
                        onClick = { pestana = 0; vm.limpiarError() },
                        text = { Text("Estudiante") }
                    )
                    Tab(
                        selected = pestana == 1,
                        onClick = { pestana = 1; vm.limpiarError() },
                        text = { Text("Soporte TI") }
                    )
                }

                AyudaTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = "Correo"
                )

                AyudaTextField(
                    value = clave,
                    onValueChange = { clave = it },
                    label = "Contraseña",
                    esPassword = true
                )

                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                AyudaButton(
                    text = "Ingresar",
                    onClick = { vm.login(correo, clave, rol.name) }
                )
            }
        }
    }
}