package com.retamozo.campusgps.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.retamozo.campusgps.model.UbicacionUi
import com.retamozo.campusgps.util.formatearDistancia
import com.retamozo.campusgps.viewmodel.MapaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugaresVisibles.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val radio by vm.radio.collectAsState()
    val dentroDelRadio by vm.idsDentroDelRadio.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val contexto = LocalContext.current

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) {
            vm.buscar()
        } else {
            vm.permisoDenegado()
        }
    }

    // Mejora: el punto azul nativo solo se activa si el permiso está concedido.
    // Se vuelve a revisar cada vez que cambia el estado (por ejemplo, tras pedir el permiso).
    val tienePermiso = remember(estado) { tienePermisoUbicacion(contexto) }

    val alPulsar: () -> Unit = {
        if (tienePermisoUbicacion(contexto)) {
            vm.buscar()
        } else {
            lanzador.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { espacio ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (estado !is UbicacionUi.Error) {
                FiltroCategorias(
                    categorias = vm.categorias,
                    seleccionada = categoria,
                    onSeleccionar = { vm.filtrarCategoria(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )

                SelectorRadio(
                    radios = vm.radiosDisponibles,
                    seleccionado = radio,
                    onSeleccionar = { vm.elegirRadio(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                MapaUbicacion(
                    estado = estado,
                    lugares = lugares,
                    seleccionado = seleccionado,
                    radio = radio,
                    mostrarMiUbicacion = tienePermiso,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = alPulsar,
                    enabled = estado != UbicacionUi.Buscando
                ) {
                    Text("Obtener mi ubicación")
                }

                when (val actual = estado) {
                    UbicacionUi.Inicial -> Text("Pulsa el botón para empezar")
                    UbicacionUi.Buscando -> CircularProgressIndicator()
                    is UbicacionUi.Lista -> {
                        Text("Latitud: ${actual.posicion.latitud}")
                        Text("Longitud: ${actual.posicion.longitud}")
                    }
                    is UbicacionUi.Error -> MensajeError(
                        error = actual,
                        onAbrirAjustes = {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", contexto.packageName, null)
                            )
                            contexto.startActivity(intent)
                        },
                        onReintentar = alPulsar,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (estado !is UbicacionUi.Error) {
                    OutlinedButton(
                        onClick = { vm.seleccionar(masCercano?.id) },
                        enabled = masCercano != null && estado != UbicacionUi.Buscando
                    ) {
                        Text("Ir al más cercano")
                    }

                    val distanciaCercana = lugares
                        .firstOrNull { it.lugar.id == masCercano?.id }?.metros
                    if (masCercano != null && distanciaCercana != null) {
                        Text(
                            text = "Más cercano: ${masCercano?.nombre} " +
                                "(${formatearDistancia(distanciaCercana)})",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            if (estado !is UbicacionUi.Error) {
                ListaLugares(
                    lugares = lugares,
                    seleccionado = seleccionado,
                    dentroDelRadio = dentroDelRadio,
                    onClick = { vm.seleccionar(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }
        }
    }
}

private fun tienePermisoUbicacion(contexto: Context): Boolean {
    val permisoFino = ContextCompat.checkSelfPermission(
        contexto,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val permisoAproximado = ContextCompat.checkSelfPermission(
        contexto,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return permisoFino || permisoAproximado
}
