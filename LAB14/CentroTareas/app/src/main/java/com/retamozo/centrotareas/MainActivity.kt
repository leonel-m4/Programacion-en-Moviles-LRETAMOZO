package com.retamozo.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.retamozo.centrotareas.data.TareasRepositoryLocal
import com.retamozo.centrotareas.ui.PantallaTareas
import com.retamozo.centrotareas.ui.theme.CentroTareasTheme
import com.retamozo.centrotareas.viewmodel.TareasViewModel
import com.retamozo.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val fabrica = TareasViewModelFactory(TareasRepositoryLocal(applicationContext))

        setContent {
            CentroTareasTheme {
                val vm: TareasViewModel = viewModel(factory = fabrica)
                PantallaTareas(vm = vm)
            }
        }
    }
}
