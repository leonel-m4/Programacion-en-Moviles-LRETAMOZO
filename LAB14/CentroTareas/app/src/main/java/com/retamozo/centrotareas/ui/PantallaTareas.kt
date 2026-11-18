package com.retamozo.centrotareas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.retamozo.centrotareas.model.CargaUi
import com.retamozo.centrotareas.model.PrimosUi
import com.retamozo.centrotareas.ui.theme.TextoExito
import com.retamozo.centrotareas.ui.theme.VerdeExito
import com.retamozo.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(vm: TareasViewModel) {
    val mensaje by vm.mensaje.collectAsState()
    val progreso by vm.progreso.collectAsState()
    val corriendo by vm.corriendo.collectAsState()
    val primos by vm.primos.collectAsState()
    val progresoPrimos by vm.progresoPrimos.collectAsState()
    val carga by vm.carga.collectAsState()
    val simularError by vm.simularError.collectAsState()
    val tiemposUnaPorUna by vm.tiemposUnaPorUna.collectAsState()
    val tiemposALaVez by vm.tiemposALaVez.collectAsState()
    val reintento by vm.reintento.collectAsState()
    val tiempoMaximo by vm.tiempoMaximo.collectAsState()
    val historial by vm.historial.collectAsState()

    // Prueba de vida: si deja de sumar, la pantalla está congelada.
    var toques by rememberSaveable { mutableIntStateOf(0) }

    // Número escrito en el campo y número usado en el último cálculo.
    var textoNumero by rememberSaveable { mutableStateOf("3000000") }
    var numeroContado by rememberSaveable { mutableIntStateOf(0) }
    val numero = textoNumero.toIntOrNull()
    val calculando = primos == PrimosUi.Calculando
    val cargando = carga == CargaUi.Cargando

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { espacio ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Tarjeta(titulo = "Tarea con progreso") {
                Button(onClick = { toques++ }) {
                    Text("Tócame: $toques")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { vm.tareaLarga() },
                        enabled = !corriendo
                    ) {
                        Text("Iniciar")
                    }
                    OutlinedButton(
                        onClick = { vm.cancelar() },
                        enabled = corriendo
                    ) {
                        Text("Cancelar")
                    }
                }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "${(progreso * 100).toInt()} %",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(text = mensaje)
            }

            Tarjeta(titulo = "Contar números primos") {
                OutlinedTextField(
                    value = textoNumero,
                    onValueChange = { nuevo ->
                        // Solo dígitos: se ignoran los textos no numéricos.
                        if (nuevo.length <= 9 && nuevo.all { it.isDigit() }) textoNumero = nuevo
                    },
                    label = { Text("Hasta el número") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            // Se ignoran los números menores que 2.
                            if (numero != null && numero >= 2) {
                                numeroContado = numero
                                vm.contar(numero)
                            }
                        },
                        enabled = numero != null && numero >= 2 && !calculando
                    ) {
                        Text("Contar")
                    }
                    OutlinedButton(
                        onClick = { vm.cancelarPrimos() },
                        enabled = calculando
                    ) {
                        Text("Cancelar")
                    }
                }
                when (val estado = primos) {
                    PrimosUi.Inicial -> {}
                    PrimosUi.Calculando -> {
                        LinearProgressIndicator(
                            progress = { progresoPrimos },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Calculando... ${(progresoPrimos * 100).toInt()} %")
                    }
                    is PrimosUi.Listo -> RecuadroExito(
                        "Primos hasta $numeroContado: ${estado.cantidad}",
                        "Tiempo: ${estado.milisegundos} ms"
                    )
                    PrimosUi.Cancelado -> Text("Cancelado")
                }
            }

            Tarjeta(titulo = "Dos cargas") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Switch(
                        checked = simularError,
                        onCheckedChange = { vm.cambiarSimularError(it) }
                    )
                    Text("Simular error")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Switch(
                        checked = tiempoMaximo,
                        onCheckedChange = { vm.cambiarTiempoMaximo(it) }
                    )
                    Text("Tiempo máximo 2 s")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { vm.cargarUnaPorUna() },
                        enabled = !cargando
                    ) {
                        Text("Una por una")
                    }
                    Button(
                        onClick = { vm.cargarALaVez() },
                        enabled = !cargando
                    ) {
                        Text("A la vez")
                    }
                }
                when (val estado = carga) {
                    CargaUi.Inicial -> {}
                    CargaUi.Cargando -> {
                        CircularProgressIndicator()
                        if (reintento > 0) Text("Reintento $reintento de 3...")
                    }
                    is CargaUi.Exito -> RecuadroExito(
                        "Modo: ${estado.modo}",
                        "Perfil: ${estado.perfil}",
                        "Pedidos: ${estado.pedidos}",
                        "Tiempo: ${estado.milisegundos} ms"
                    )
                    is CargaUi.Error -> {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ) {
                            Text(
                                text = estado.mensaje,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        OutlinedButton(onClick = { vm.cargarALaVez() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            Tarjeta(titulo = "Comparación de tiempos") {
                TablaTiempos(tiemposUnaPorUna, tiemposALaVez)
            }

            Tarjeta(titulo = "Historial (últimos 5)") {
                if (historial.isEmpty()) {
                    Text(
                        text = "Sin resultados guardados.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    historial.forEachIndexed { indice, resultado ->
                        Text(
                            text = "${indice + 1}. $resultado",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

// Tabla con los tiempos acumulados de cada modo y la diferencia entre los promedios.
@Composable
private fun TablaTiempos(unaPorUna: List<Long>, aLaVez: List<Long>) {
    if (unaPorUna.isEmpty() && aLaVez.isEmpty()) {
        Text(
            text = "Aún no hay tiempos. Usa los botones de Dos cargas.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }
    FilaTabla("Modo", "Veces", "Último", "Promedio", negrita = true)
    FilaTabla(
        "Una por una",
        "${unaPorUna.size}",
        unaPorUna.lastOrNull()?.let { "$it ms" } ?: "-",
        promedio(unaPorUna)?.let { "$it ms" } ?: "-"
    )
    FilaTabla(
        "A la vez",
        "${aLaVez.size}",
        aLaVez.lastOrNull()?.let { "$it ms" } ?: "-",
        promedio(aLaVez)?.let { "$it ms" } ?: "-"
    )
    val promedioUna = promedio(unaPorUna)
    val promedioVez = promedio(aLaVez)
    if (promedioUna != null && promedioVez != null) {
        Text(
            text = "A la vez ahorra ${promedioUna - promedioVez} ms en promedio",
            fontWeight = FontWeight.Bold
        )
    }
}

private fun promedio(tiempos: List<Long>): Long? =
    if (tiempos.isEmpty()) null else tiempos.sum() / tiempos.size

@Composable
private fun FilaTabla(
    modo: String,
    veces: String,
    ultimo: String,
    promedio: String,
    negrita: Boolean = false
) {
    val peso = if (negrita) FontWeight.Bold else FontWeight.Normal
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(modo, modifier = Modifier.weight(1.4f), fontWeight = peso)
        Text(veces, modifier = Modifier.weight(0.8f), fontWeight = peso, textAlign = TextAlign.End)
        Text(ultimo, modifier = Modifier.weight(1.1f), fontWeight = peso, textAlign = TextAlign.End)
        Text(promedio, modifier = Modifier.weight(1.1f), fontWeight = peso, textAlign = TextAlign.End)
    }
}

// Tarjeta blanca con borde, título azul y contenido centrado.
@Composable
private fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            contenido()
        }
    }
}

// Recuadro verde con el resultado: la primera línea en negrita.
@Composable
private fun RecuadroExito(titulo: String, vararg lineas: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = VerdeExito,
        contentColor = TextoExito
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titulo, fontWeight = FontWeight.Bold)
            lineas.forEach { Text(text = it) }
        }
    }
}
