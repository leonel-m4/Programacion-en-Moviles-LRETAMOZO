package com.retamozo.temperaturedisplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.retamozo.temperaturedisplay.ui.theme.TemperatureDisplayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme() {
                TemperatureDisplay()
            }
        }
    }
}

@Composable
fun TemperatureDisplay() {
    var temperatura by remember { mutableStateOf(20) }
    val colorTemperatura = when {
        temperatura > 30 -> Color.Red
        temperatura < 10 -> Color.Blue
        else -> Color.Black
    }
    Text(
        text = "Temperatura: $temperatura °C",
        color = colorTemperatura
    )
    Row {
        Button(
            onClick = { temperatura++ }
        ) {
            Text("Subir")
        }

        Button(
            onClick = { temperatura-- }
        ) {
            Text("Bajar")
        }
    }
    Button(
        onClick = { temperatura = 20 }
    ) {
        Text("Resetear")
    }
}