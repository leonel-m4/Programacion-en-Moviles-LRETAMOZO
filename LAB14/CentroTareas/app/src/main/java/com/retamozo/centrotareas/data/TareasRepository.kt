package com.retamozo.centrotareas.data

import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    // Mejora: la tarea simulada vive en el Repository y expone su avance (0f a 1f).
    fun tareaConProgreso(): Flow<Float>

    // Mejora: avisa el avance del cálculo (0f a 1f) mientras cuenta.
    suspend fun contarPrimos(hasta: Int, alAvanzar: (Float) -> Unit = {}): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>

    // Mejora: los últimos 5 resultados guardados con DataStore (el más reciente primero).
    val historial: Flow<List<String>>
    suspend fun guardarEnHistorial(resultado: String)
}
