package com.retamozo.campusgps.data

import com.retamozo.campusgps.model.Posicion

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
}
