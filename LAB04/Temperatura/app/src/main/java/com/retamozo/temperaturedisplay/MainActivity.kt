package com.retamozo.temperaturedisplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.retamozo.temperaturedisplay.ui.theme.TemperatureDisplayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemperatureDisplayTheme {
                Scaffold {
                    TemperatureDisplay()
                }
            }
        }
    }
}

@Composable
fun TemperatureDisplay() {

}