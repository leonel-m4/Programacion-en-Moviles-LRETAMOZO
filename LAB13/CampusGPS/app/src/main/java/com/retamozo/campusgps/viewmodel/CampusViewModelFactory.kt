package com.retamozo.campusgps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.retamozo.campusgps.data.LugaresRepository
import com.retamozo.campusgps.data.LugaresRepositoryLocal
import com.retamozo.campusgps.data.PreferenciasRepository
import com.retamozo.campusgps.data.PreferenciasRepositoryDataStore
import com.retamozo.campusgps.data.UbicacionRepository
import com.retamozo.campusgps.data.UbicacionRepositoryGoogle

class CampusViewModelFactory(contexto: Context) : ViewModelProvider.Factory {

    private val ubicacionRepo: UbicacionRepository =
        UbicacionRepositoryGoogle(contexto.applicationContext)
    private val lugaresRepo: LugaresRepository = LugaresRepositoryLocal()
    private val preferenciasRepo: PreferenciasRepository =
        PreferenciasRepositoryDataStore(contexto.applicationContext)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(MapaViewModel::class.java)) {
            "ViewModel no soportado: ${modelClass.name}"
        }
        return MapaViewModel(ubicacionRepo, lugaresRepo, preferenciasRepo) as T
    }
}
