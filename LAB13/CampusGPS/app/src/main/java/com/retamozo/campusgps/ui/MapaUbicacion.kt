package com.retamozo.campusgps.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.retamozo.campusgps.model.LugarConDistancia
import com.retamozo.campusgps.model.UbicacionUi

// El permiso lo revisa PantallaMapa antes de pasar mostrarMiUbicacion = true.
@SuppressLint("MissingPermission")
@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    modifier: Modifier = Modifier,
    radio: Int? = null,
    mostrarMiUbicacion: Boolean = false
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista && seleccionado == null) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    16f
                )
            )
        }
    }

    LaunchedEffect(seleccionado) {
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
        if (lugar != null) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(lugar.latitud, lugar.longitud),
                    17f
                )
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara,
        properties = MapProperties(isMyLocationEnabled = mostrarMiUbicacion),
        uiSettings = MapUiSettings(myLocationButtonEnabled = mostrarMiUbicacion)
    ) {
        if (estado is UbicacionUi.Lista) {
            Marker(
                state = rememberUpdatedMarkerState(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud)
                ),
                title = "Estás aquí",
                contentDescription = "Estás aquí"
            )

            if (radio != null) {
                Circle(
                    center = LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    radius = radio.toDouble(),
                    fillColor = Color(0x223388CC),
                    strokeColor = Color(0xFF3388CC),
                    strokeWidth = 3f
                )
            }
        }

        lugares.forEach { item ->
            key(item.lugar.id) {
                // El ViewModel entrega la lista ordenada; aquí solo se elige el color.
                val esMasCercano = item.metros != null && item == lugares.firstOrNull()
                val marcador = rememberUpdatedMarkerState(
                    LatLng(item.lugar.latitud, item.lugar.longitud)
                )
                Marker(
                    state = marcador,
                    title = item.lugar.nombre,
                    snippet = item.lugar.categoria,
                    contentDescription = "${item.lugar.nombre}, ${item.lugar.categoria}",
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (esMasCercano) {
                            BitmapDescriptorFactory.HUE_GREEN
                        } else {
                            BitmapDescriptorFactory.HUE_AZURE
                        }
                    )
                )

                LaunchedEffect(seleccionado) {
                    if (seleccionado == item.lugar.id) {
                        marcador.showInfoWindow()
                    }
                }
            }
        }
    }
}
