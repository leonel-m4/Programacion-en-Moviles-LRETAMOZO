package com.retamozo.avisoscampus

import android.app.Application
import com.retamozo.avisoscampus.notificacion.Notificador

class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
