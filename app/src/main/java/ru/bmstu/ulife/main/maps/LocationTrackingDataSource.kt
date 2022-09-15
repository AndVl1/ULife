package ru.bmstu.ulife.main.maps

import android.Manifest
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import ru.bmstu.ulife.utils.UserLocation
import ru.bmstu.ulife.utils.isPermissionGranted
import java.util.concurrent.TimeUnit

class LocationTrackingDataSource(
    private val context: Context,
    private val client: FusedLocationProviderClient,
) {
    private val locationRequest = LocationRequest.create().apply {
        interval = TimeUnit.SECONDS.toMillis(10)
        fastestInterval = TimeUnit.SECONDS.toMillis(5)
        priority = PRIORITY_HIGH_ACCURACY
        this.maxWaitTime = TimeUnit.MINUTES.toMillis(10)
        smallestDisplacement = 20f
    }

    fun getUserLocationFlow() = callbackFlow {
        while (!isLocationPermissionGranted()) {
            delay(TimeUnit.SECONDS.toMillis(1))
        }
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val userLocation = UserLocation(LatLng(it.latitude, it.longitude), true)
                    this@callbackFlow.trySend(userLocation)
                }
            }
        }

        client.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
            .addOnFailureListener {
                Log.e(TAG, "$it")
                close(it)
            }
        awaitClose { client.removeLocationUpdates(callback) }
    }

    private fun isLocationPermissionGranted() : Boolean {
        return context.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    companion object {
        private val moscow = UserLocation(LatLng(55.751244, 37.618423), false)
        private const val TAG = "LocationDS"
    }
}
