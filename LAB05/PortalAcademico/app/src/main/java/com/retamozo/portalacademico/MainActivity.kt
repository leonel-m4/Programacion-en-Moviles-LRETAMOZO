package com.retamozo.portalacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.retamozo.portalacademico.navigation.AppNavigation
import com.retamozo.portalacademico.ui.theme.PortalAcademicoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortalAcademicoTheme {
                AppNavigation()
            }
        }
    }
}
