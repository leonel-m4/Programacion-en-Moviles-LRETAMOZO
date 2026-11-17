package com.retamozo.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.retamozo.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

/**
 * Google Play Services queda dentro del Repository, no en el ViewModel.
 */
class UbicacionRepositoryGoogle(contexto: Context) : UbicacionRepository {

    private val cliente = LocationServices.getFusedLocationProviderClient(
        contexto.applicationContext
    )

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val ubicacion = cliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancelacion.token
            ).await()

            if (ubicacion == null) {
                Result.failure(IllegalStateException("Activa el GPS e intenta de nuevo"))
            } else {
                Result.success(Posicion(ubicacion.latitude, ubicacion.longitude))
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(error)
        } finally {
            cancelacion.cancel()
        }
    }
}
