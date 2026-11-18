package com.retamozo.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retamozo.campusgps.data.LugaresRepository
import com.retamozo.campusgps.data.UbicacionRepository
import com.retamozo.campusgps.model.Lugar
import com.retamozo.campusgps.model.LugarConDistancia
import com.retamozo.campusgps.model.Posicion
import com.retamozo.campusgps.model.TipoError
import com.retamozo.campusgps.model.UbicacionUi
import com.retamozo.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    // Mejora: filtro por categoría (null = todas).
    val categorias: List<String> = lugaresRepo.obtenerLugares().map { it.categoria }.distinct()

    private val _categoria = MutableStateFlow<String?>(null)
    val categoria: StateFlow<String?> = _categoria.asStateFlow()

    // Lo que ven el mapa y la lista: la lista ordenada con el filtro aplicado.
    val lugaresVisibles: StateFlow<List<LugarConDistancia>> =
        combine(_lugares, _categoria) { lista, categoria ->
            if (categoria == null) lista else lista.filter { it.lugar.categoria == categoria }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _lugares.value)

    init {
        // Evita intentar dibujar el mapa en un dispositivo sin Play Services.
        comprobarPlayServices()
    }

    fun buscar() {
        if (_estado.value == UbicacionUi.Buscando) return
        if (!comprobarPlayServices()) return
        _seleccionado.value = null
        _estado.value = UbicacionUi.Buscando

        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    actualizarDistancias(it)
                    _estado.value = UbicacionUi.Lista(it)
                }
                .onFailure {
                    _estado.value = UbicacionUi.Error(mensajeDe(it), tipoDe(it))
                }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            mensaje = "Sin permiso de ubicación no puedo mostrar dónde estás",
            tipo = TipoError.SIN_PERMISO
        )
    }

    private fun actualizarDistancias(pos: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { lugar ->
                LugarConDistancia(lugar, distanciaMetros(pos, lugar))
            }
            .sortedBy { it.metros }
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    fun filtrarCategoria(categoria: String?) {
        _categoria.value = categoria
        // Si el lugar seleccionado queda oculto por el filtro, se quita la selección.
        val id = _seleccionado.value ?: return
        val sigueVisible = categoria == null ||
            _lugares.value.any { it.lugar.id == id && it.lugar.categoria == categoria }
        if (!sigueVisible) _seleccionado.value = null
    }

    // El más cercano se busca entre los lugares visibles con el filtro actual.
    val masCercano: StateFlow<Lugar?> = lugaresVisibles.map { lista ->
        lista.firstOrNull { it.metros != null }?.lugar
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private fun comprobarPlayServices(): Boolean {
        if (ubicacionRepo.playServicesDisponible()) return true

        _estado.value = UbicacionUi.Error(
            mensaje = "Este dispositivo no tiene Google Play Services",
            tipo = TipoError.SIN_PLAY_SERVICES
        )
        return false
    }

    private fun mensajeDe(error: Throwable): String = when (error) {
        is SecurityException -> "Sin permiso de ubicación no puedo mostrar dónde estás"
        is IllegalStateException -> "Activa el GPS e intenta de nuevo"
        else -> "No se pudo obtener la ubicación. Intenta de nuevo"
    }

    private fun tipoDe(error: Throwable): TipoError = when (error) {
        is SecurityException -> TipoError.SIN_PERMISO
        is IllegalStateException -> TipoError.GPS_APAGADO
        else -> TipoError.OTRO
    }
}
