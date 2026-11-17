package com.retamozo.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retamozo.campusgps.data.LugaresRepository
import com.retamozo.campusgps.data.UbicacionRepository
import com.retamozo.campusgps.model.LugarConDistancia
import com.retamozo.campusgps.model.Posicion
import com.retamozo.campusgps.model.UbicacionUi
import com.retamozo.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    fun buscar() {
        if (_estado.value == UbicacionUi.Buscando) return
        _estado.value = UbicacionUi.Buscando

        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    actualizarDistancias(it)
                    _estado.value = UbicacionUi.Lista(it)
                }
                .onFailure {
                    _estado.value = UbicacionUi.Error(mensajeDe(it))
                }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás"
        )
    }

    private fun actualizarDistancias(pos: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { lugar ->
                LugarConDistancia(lugar, distanciaMetros(pos, lugar))
            }
            .sortedBy { it.metros }
    }

    // TODO Paso 6: seleccionar(id: Int?).
    // TODO Paso 7: masCercano: StateFlow<Lugar?>.

    private fun mensajeDe(error: Throwable): String = when (error) {
        is SecurityException -> "Falta el permiso de ubicación"
        else -> error.message ?: "No se pudo obtener la ubicación"
    }
}
