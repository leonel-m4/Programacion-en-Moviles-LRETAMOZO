package com.retamozo.campusgps.data

import kotlinx.coroutines.flow.Flow

interface PreferenciasRepository {
    // Id del último lugar seleccionado, o null si todavía no hay ninguno.
    fun ultimoLugar(): Flow<Int?>
    suspend fun guardarUltimoLugar(id: Int)
}
