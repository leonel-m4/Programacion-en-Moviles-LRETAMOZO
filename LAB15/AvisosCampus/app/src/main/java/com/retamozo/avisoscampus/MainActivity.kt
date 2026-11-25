package com.retamozo.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.retamozo.avisoscampus.data.AvisosRepositoryMemoria
import com.retamozo.avisoscampus.ui.PantallaAvisos
import com.retamozo.avisoscampus.ui.theme.AvisosCampusTheme
import com.retamozo.avisoscampus.viewmodel.AvisosViewModel
import com.retamozo.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {

    private val vm: AvisosViewModel by viewModels {
        AvisosViewModelFactory(AvisosRepositoryMemoria())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvisosCampusTheme {
                PantallaAvisos(vm)
            }
        }
    }
}
