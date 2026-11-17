package com.retamozo.campusgps.util

import android.location.Location
import com.retamozo.campusgps.model.Lugar
import com.retamozo.campusgps.model.Posicion

/**
 * Calcula metros sin guardar estado ni depender de la pantalla.
 */
fun distanciaMetros(desde: Posicion, hasta: Lugar): Float {
    val resultado = FloatArray(1)
    Location.distanceBetween(
        desde.latitud,
        desde.longitud,
        hasta.latitud,
        hasta.longitud,
        resultado
    )
    return resultado[0]
}
