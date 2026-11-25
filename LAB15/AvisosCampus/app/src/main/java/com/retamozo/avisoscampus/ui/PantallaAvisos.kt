package com.retamozo.avisoscampus.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.retamozo.avisoscampus.notificacion.Notificador
import com.retamozo.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // TODO Paso 8: si no se mostró, avisar al ViewModel
        }
    }

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        // TODO: vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        // TODO: si Notificador.tienePermiso(contexto), vm.permisoRespondido(true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Tarjeta("Permiso de notificaciones") {
                // TODO: etiqueta Sin pedir / Permitido / Denegado
                // TODO: botón "Permitir notificaciones"
                // API 33+ -> lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                Button(onClick = { }) {
                    Text("Permitir notificaciones")
                }
            }

            // TODO Paso 8: recuadro de error con el mensaje y el botón "Abrir ajustes"

            Tarjeta("Avisos") {
                // TODO Paso 3: llamar a vm.avisarAhora()
                Button(onClick = { }) {
                    Text("Mostrar aviso")
                }
            }

            Tarjeta("Recordatorio") {
                // TODO Paso 5: recordarEn(), cancelarRecordatorio() y el texto "Faltan N s"
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { }) {
                        Text("Recordar en 10 s")
                    }
                    OutlinedButton(onClick = { }) {
                        Text("Cancelar")
                    }
                }
            }

            Tarjeta("Receptor de anuncios") {
                // TODO Paso 6: conectar el Switch con vm.cambiarEscucha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Escuchar el modo avión", modifier = Modifier.weight(1f))
                    Switch(checked = false, onCheckedChange = { })
                }
            }

            // TODO Paso 7: HistorialAvisos(avisos)
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            contenido()
        }
    }
}
