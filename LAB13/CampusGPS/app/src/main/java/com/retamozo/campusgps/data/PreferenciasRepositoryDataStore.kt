package com.retamozo.campusgps.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Una sola instancia de DataStore por archivo en toda la app.
private val Context.preferenciasCampus: DataStore<Preferences> by preferencesDataStore(
    name = "preferencias_campus"
)

/**
 * DataStore queda dentro del Repository: el ViewModel solo conoce la interface.
 */
class PreferenciasRepositoryDataStore(contexto: Context) : PreferenciasRepository {

    private val dataStore = contexto.applicationContext.preferenciasCampus

    override fun ultimoLugar(): Flow<Int?> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferencias -> preferencias[ULTIMO_LUGAR] }

    override suspend fun guardarUltimoLugar(id: Int) {
        dataStore.edit { preferencias -> preferencias[ULTIMO_LUGAR] = id }
    }

    private companion object {
        val ULTIMO_LUGAR = intPreferencesKey("ultimo_lugar")
    }
}
