package com.weatheraanalyzerrrr.util

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

private const val TAG = "LocationFile"

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}

class DefaultLocationTracker(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { cont ->

            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) {} // Resume coroutine with location result
                    } else {
                        val locationRequest: LocationRequest = LocationRequest()
                        locationRequest.interval = 10000
                        locationRequest.fastestInterval = 1000
                        locationRequest.numUpdates = 1
                        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                        val locationCallback: LocationCallback = object : LocationCallback() {
                            override fun onLocationResult(p0: LocationResult) {
                                super.onLocationResult(p0)
                                val location =
                                    p0.lastLocation
                                if (location != null) {
                                    // get latest location
                                    cont.resume(location) // Resume coroutine with null location result
                                    // use your location object
                                    // get latitude , longitude and other info from this
                                } else {
                                    cont.resume(null) // Resume coroutine with null location result
                                    Log.d(TAG, "onLocationResult: empty ")
                                }
                            }

                        }
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            null /* Looper */
                        )

                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) {}  // Resume coroutine with location result
                }
                addOnFailureListener {
                    val locationRequest: LocationRequest =
                        com.google.android.gms.location.LocationRequest()
                    locationRequest.interval = 10000
                    locationRequest.fastestInterval = 1000
                    locationRequest.numUpdates = 1
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            super.onLocationResult(p0)
                            val location =
                                p0.lastLocation
                            if (location != null) {
                                // get latest location
                                cont.resume(location) // Resume coroutine with null location result
                                // use your location object
                                // get latitude , longitude and other info from this
                            } else {
                                cont.resume(null) // Resume coroutine with null location result
                                Log.d(TAG, "onLocationResult: empty ")
                            }
                        }

                    }
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null /* Looper */
                    )

                }
                addOnCanceledListener {
                    cont.cancel() // Cancel the coroutine
                }
            }
        }
    }
}