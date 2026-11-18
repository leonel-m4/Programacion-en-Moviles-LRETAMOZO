package com.retamozo.centrotareas.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

// Un solo DataStore para toda la app, guardado en el archivo "historial".
private val Context.dataStore by preferencesDataStore(name = "historial")

/**
 * Cargas simuladas con delay. Los datos son ficticios.
 */
class TareasRepositoryLocal(private val context: Context) : TareasRepository {

    // Los resultados se guardan en una sola cadena, uno por línea.
    override val historial: Flow<List<String>> = context.dataStore.data.map { preferencias ->
        leerLista(preferencias[CLAVE_HISTORIAL])
    }

    override suspend fun guardarEnHistorial(resultado: String) {
        try {
            context.dataStore.edit { preferencias ->
                val actual = leerLista(preferencias[CLAVE_HISTORIAL])
                preferencias[CLAVE_HISTORIAL] =
                    (listOf(resultado) + actual).take(MAX_HISTORIAL).joinToString(SEPARADOR)
            }
        } catch (e: IOException) {
            // Si no se puede escribir, la app sigue funcionando sin guardar ese resultado.
            Log.e("HISTORIAL", "No se pudo guardar el resultado", e)
        }
    }

    private fun leerLista(texto: String?): List<String> =
        texto?.split(SEPARADOR)?.filter { it.isNotBlank() } ?: emptyList()

    // 100 vueltas de 40 ms (unos 4 s). flowOn hace que las emisiones corran en Dispatchers.Default.
    override fun tareaConProgreso(): Flow<Float> = flow {
        Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
        for (i in 1..100) {
            delay(40)
            emit(i / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    // Cálculo pesado: va en Dispatchers.Default para no bloquear el hilo principal.
    override suspend fun contarPrimos(hasta: Int, alAvanzar: (Float) -> Unit): Int =
        withContext(Dispatchers.Default) {
            var cantidad = 0
            val total = (hasta - 1).toLong()
            var ultimoPorcentaje = -1
            for (n in 2..hasta) {
                // Permite cancelar el cálculo a mitad de camino.
                ensureActive()
                if (esPrimo(n)) cantidad++
                // Solo avisa cuando cambia el porcentaje entero, para no saturar la pantalla.
                val porcentaje = ((n - 1) * 100L / total).toInt()
                if (porcentaje != ultimoPorcentaje) {
                    ultimoPorcentaje = porcentaje
                    alAvanzar(porcentaje / 100f)
                }
            }
            cantidad
        }

    private fun esPrimo(n: Int): Boolean {
        if (n < 2) return false
        var d = 2
        // d * d en Long evita desbordes; equivale a probar hasta la raíz de n.
        while (d.toLong() * d <= n) {
            if (n % d == 0) return false
            d++
        }
        return true
    }

    private companion object {
        val CLAVE_HISTORIAL = stringPreferencesKey("ultimos_resultados")
        const val SEPARADOR = "\n"
        const val MAX_HISTORIAL = 5
    }
}
