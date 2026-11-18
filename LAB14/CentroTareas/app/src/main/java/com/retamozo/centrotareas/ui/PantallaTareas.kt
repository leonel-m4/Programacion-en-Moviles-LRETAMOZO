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
    val carga by vm.carga.collectAsState()

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
                    PrimosUi.Calculando -> Text("Calculando...")
                    is PrimosUi.Listo -> RecuadroExito(
                        "Primos hasta $numeroContado: ${estado.cantidad}",
                        "Tiempo: ${estado.milisegundos} ms"
                    )
                    PrimosUi.Cancelado -> Text("Cancelado")
                }
            }

            Tarjeta(titulo = "Dos cargas") {
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
                    CargaUi.Cargando -> CircularProgressIndicator()
                    is CargaUi.Exito -> RecuadroExito(
                        "Modo: ${estado.modo}",
                        "Perfil: ${estado.perfil}",
                        "Pedidos: ${estado.pedidos}",
                        "Tiempo: ${estado.milisegundos} ms"
                    )
                    is CargaUi.Error -> Text(text = estado.mensaje)
                }
            }
        }
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
