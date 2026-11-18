package com.retamozo.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retamozo.centrotareas.data.TareasRepository
import com.retamozo.centrotareas.model.CargaUi
import com.retamozo.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso (pasos 1 a 4)
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    // Paso 3: avance de 0f a 1f. La pantalla solo recibe la versión de lectura.
    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    // Paso 4: el Job permite cancelar; corriendo evita lanzar dos tareas a la vez.
    private var trabajo: Job? = null

    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    // El trabajo lo hace el Repository en segundo plano; aquí solo se recoge el avance.
    fun tareaLarga() {
        if (_corriendo.value) return
        _corriendo.value = true

        trabajo = viewModelScope.launch {
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.tareaConProgreso().collect { avance ->
                    _progreso.value = avance
                }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                // Se ejecuta tanto si termina bien como si la cancelan.
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        if (!_corriendo.value) return
        trabajo?.cancel()
        // La barra se queda donde estaba.
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos (paso 5)
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()
    private var trabajoPrimos: Job? = null

    // Mejora: porcentaje del cálculo de primos (0f a 1f).
    private val _progresoPrimos = MutableStateFlow(0f)
    val progresoPrimos: StateFlow<Float> = _progresoPrimos.asStateFlow()

    fun contar(hasta: Int) {
        if (_primos.value == PrimosUi.Calculando) return
        _progresoPrimos.value = 0f
        _primos.value = PrimosUi.Calculando

        trabajoPrimos = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta) { avance ->
                    _progresoPrimos.value = avance
                }
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                // Se vuelve a lanzar para no romper la cancelación.
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7)
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    // Paso 7: interruptor para que los pedidos fallen con IOException("Sin conexión").
    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    // Mejora: tiempos acumulados de cada modo para compararlos en una tabla.
    private val _tiemposUnaPorUna = MutableStateFlow<List<Long>>(emptyList())
    val tiemposUnaPorUna: StateFlow<List<Long>> = _tiemposUnaPorUna.asStateFlow()

    private val _tiemposALaVez = MutableStateFlow<List<Long>>(emptyList())
    val tiemposALaVez: StateFlow<List<Long>> = _tiemposALaVez.asStateFlow()

    // Mejora: número del reintento en curso (0 = primer intento).
    private val _reintento = MutableStateFlow(0)
    val reintento: StateFlow<Int> = _reintento.asStateFlow()

    // Reintenta hasta MAX_REINTENTOS veces con espera creciente (500, 1000, 2000 ms).
    // Si el último intento también falla, el error sube para mostrarlo en pantalla.
    private suspend fun <T> conReintentos(bloque: suspend () -> T): T {
        var espera = ESPERA_INICIAL_MS
        repeat(MAX_REINTENTOS) { numero ->
            try {
                return bloque()
            } catch (e: CancellationException) {
                // La cancelación nunca se reintenta.
                throw e
            } catch (e: Exception) {
                _reintento.value = numero + 1
                delay(espera)
                espera *= 2
            }
        }
        return bloque()
    }

    // Mejora: interruptor para limitar las cargas a 2 s con withTimeout.
    private val _tiempoMaximo = MutableStateFlow(false)
    val tiempoMaximo: StateFlow<Boolean> = _tiempoMaximo.asStateFlow()

    fun cambiarTiempoMaximo(valor: Boolean) {
        _tiempoMaximo.value = valor
    }

    // Con el interruptor activado, toda la carga (incluidos los reintentos) tiene 2 s como máximo.
    // Si se pasa, withTimeout lanza TimeoutCancellationException y la carga se detiene.
    private suspend fun <T> ejecutarCarga(bloque: suspend () -> T): T =
        if (_tiempoMaximo.value) {
            withTimeout(TIEMPO_MAXIMO_MS) { conReintentos(bloque) }
        } else {
            conReintentos(bloque)
        }

    // Una carga detrás de otra: 2 s + 3 s ≈ 5 s.
    fun cargarUnaPorUna() {
        if (_carga.value == CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        _reintento.value = 0

        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = ejecutarCarga {
                    val perfil = repo.cargarPerfil()
                    val pedidos = repo.cargarPedidos(_simularError.value)
                    Pair(perfil, pedidos)
                }
                val milisegundos = System.currentTimeMillis() - inicio
                _tiemposUnaPorUna.value = _tiemposUnaPorUna.value + milisegundos
                _carga.value = CargaUi.Exito(
                    modo = "Una por una",
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = milisegundos
                )
            } catch (e: TimeoutCancellationException) {
                // Va antes que CancellationException porque es un tipo de cancelación.
                _carga.value = CargaUi.Error("Tardó demasiado")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    // Las dos cargas a la vez con async: máximo(2 s, 3 s) ≈ 3 s.
    fun cargarALaVez() {
        if (_carga.value == CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        _reintento.value = 0

        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                // Si una carga falla, coroutineScope cancela la otra y lanza el error aquí.
                val (perfil, pedidos) = ejecutarCarga {
                    coroutineScope {
                        val perfil = async { repo.cargarPerfil() }
                        val pedidos = async { repo.cargarPedidos(_simularError.value) }
                        Pair(perfil.await(), pedidos.await())
                    }
                }
                val milisegundos = System.currentTimeMillis() - inicio
                _tiemposALaVez.value = _tiemposALaVez.value + milisegundos
                _carga.value = CargaUi.Exito(
                    modo = "A la vez",
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = milisegundos
                )
            } catch (e: TimeoutCancellationException) {
                // Va antes que CancellationException porque es un tipo de cancelación.
                _carga.value = CargaUi.Error("Tardó demasiado")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    companion object {
        private const val MAX_REINTENTOS = 3
        private const val ESPERA_INICIAL_MS = 500L
        private const val TIEMPO_MAXIMO_MS = 2000L
    }
}
