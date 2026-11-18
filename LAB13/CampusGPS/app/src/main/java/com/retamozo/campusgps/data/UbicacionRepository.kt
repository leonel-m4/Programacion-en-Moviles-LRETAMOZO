package com.retamozo.campusgps.data

import com.retamozo.campusgps.model.Posicion
import kotlinx.coroutines.flow.Flow

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
    fun playServicesDisponible(): Boolean

    // Mejora: emite la posición cada vez que cambia, hasta que se deja de recolectar.
    fun seguirPosicion(): Flow<Posicion>
}
