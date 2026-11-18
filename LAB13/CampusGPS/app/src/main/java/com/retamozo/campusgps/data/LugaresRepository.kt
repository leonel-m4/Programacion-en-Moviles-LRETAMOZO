package com.retamozo.campusgps.data

import com.retamozo.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
