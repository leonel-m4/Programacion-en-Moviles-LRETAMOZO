package com.retamozo.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
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

    private val contexto = contexto.applicationContext
    private val cliente = LocationServices.getFusedLocationProviderClient(
        this.contexto
    )

    override fun playServicesDisponible(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS
    }

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val gestor = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!LocationManagerCompat.isLocationEnabled(gestor)) {
                return Result.failure(
                    IllegalStateException("Activa el GPS e intenta de nuevo")
                )
            }

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
