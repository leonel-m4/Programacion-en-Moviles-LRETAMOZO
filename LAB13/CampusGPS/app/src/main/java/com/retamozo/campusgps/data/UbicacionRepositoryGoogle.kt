package com.retamozo.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Looper
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.retamozo.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    /**
     * requestLocationUpdates convertido en Flow: cada resultado se envía al canal y,
     * cuando el ViewModel deja de recolectar, awaitClose quita el callback.
     */
    @SuppressLint("MissingPermission")
    override fun seguirPosicion(): Flow<Posicion> = callbackFlow {
        val gestor = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!LocationManagerCompat.isLocationEnabled(gestor)) {
            throw IllegalStateException("Activa el GPS e intenta de nuevo")
        }

        val solicitud = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000L)
            .setMinUpdateDistanceMeters(2f)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(resultado: LocationResult) {
                val ubicacion = resultado.lastLocation ?: return
                trySend(Posicion(ubicacion.latitude, ubicacion.longitude))
            }
        }

        cliente.requestLocationUpdates(solicitud, callback, Looper.getMainLooper()).await()
        awaitClose { cliente.removeLocationUpdates(callback) }
    }
}
