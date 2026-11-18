package com.retamozo.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.retamozo.campusgps.ui.PantallaMapa
import com.retamozo.campusgps.ui.theme.CampusGPSTheme
import com.retamozo.campusgps.viewmodel.CampusViewModelFactory
import com.retamozo.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val fabrica = CampusViewModelFactory(applicationContext)

        setContent {
            CampusGPSTheme {
                val vm: MapaViewModel = viewModel(factory = fabrica)
                PantallaMapa(vm = vm)
            }
        }
    }
}
