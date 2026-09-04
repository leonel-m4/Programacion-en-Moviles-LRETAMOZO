package com.retamozo.registrodenotas_tarea

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retamozo.registrodenotas_tarea.ui.theme.RegistroDeNotasTareaTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeNotasTareaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Registro de Notas",
                                    color = Color(0xFFFFFFFF),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    ) {
                        PantallaRegistro()
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaRegistro(modifier: Modifier = Modifier) {
    var FDP by remember { mutableStateOf(0f) }
    var POO by remember { mutableStateOf(0f) }
    var PM by remember { mutableStateOf(0f) }
    var BD by remember { mutableStateOf(0f) }
    var RedondearPromedio by remember { mutableStateOf(false) }
    var confirmarNotas by remember { mutableStateOf(false) }
    var promedioCalculado by remember { mutableStateOf(false) }
    var promedioPonderado by remember { mutableStateOf(0.0) }
    var promedioFinal by remember { mutableStateOf(0.0) }
    var observacion by remember { mutableStateOf("") }
    var colorObservacion by remember { mutableStateOf(Color.Transparent) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notas del ciclo",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Desliza para asignar cada nota (0 a 20)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(24.dp))

        //FDP
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Fundamentos de Programación (20%)",
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = FDP,
                    onValueChange = { FDP = it.toInt().toFloat()},
                    valueRange = 0f..20f,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = FDP.toInt().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //POO
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Programación Orientada a Objetos (25%)"
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = POO,
                    onValueChange = { POO = it.toInt().toFloat()},
                    valueRange = 0f..20f,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = POO.toInt().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //PM
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Programación en Móviles (30%)"
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = PM,
                    onValueChange = { PM = it.toInt().toFloat()},
                    valueRange = 0f..20f,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = PM.toInt().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //BD
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Base de Datos (25%)"
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = BD,
                    onValueChange = { BD = it.toInt().toFloat()},
                    valueRange = 0f..20f,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = BD.toInt().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Redondear promedio final",
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = RedondearPromedio,
                onCheckedChange = { RedondearPromedio = it }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = confirmarNotas,
                onCheckedChange = { confirmarNotas = it }
            )
            Text(
                text = "Confirmo que las notas son correctas"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                promedioPonderado =
                            (FDP * 0.20) +
                            (POO * 0.25) +
                            (PM * 0.30) +
                            (BD * 0.25)
                promedioFinal = if (RedondearPromedio) {
                    promedioPonderado.roundToInt().toDouble()
                } else {
                    promedioPonderado
                }
                observacion = when {
                    promedioFinal >= 17 -> "EXCELENTE"
                    promedioFinal >= 13 -> "APROBADO"
                    promedioFinal >= 10 -> "EN RECUPERACIÓN"
                    else -> "DESAPROBADO"
                }
                colorObservacion = when {
                    promedioFinal >= 17 -> Color(0xFF1B5E20)
                    promedioFinal >= 13 -> Color(0xFF4CAF50)
                    promedioFinal >= 10 -> Color(0xFFFFC107)
                    else -> Color(0xFFF44336)
                }
                promedioCalculado = true
            },
            enabled = confirmarNotas,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "CALCULAR PROMEDIO"
            )
        }
        if (!promedioCalculado) {
            Text(
                text = "Asigna las notas y confirma para calcular",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        if (promedioCalculado) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Promedio ponderado: %.2f".format(promedioPonderado)
                    )
                    Text(
                        text = if (RedondearPromedio) {
                            "Promedio final: ${promedioFinal.toInt()} (redondeado)"
                        } else {
                            "Promedio final: %.2f".format(promedioFinal)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = colorObservacion,
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = observacion,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            )
                        )
                    }
                }
            }
        }
    }
}