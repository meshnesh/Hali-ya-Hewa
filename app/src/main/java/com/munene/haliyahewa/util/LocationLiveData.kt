package com.munene.haliyahewa.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.munene.haliyahewa.data.datastore.HaliYaHewaStore
import com.munene.haliyahewa.data.models.UserLocation

class LocationLiveData constructor(val context: Context) : LiveData<UserLocation>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                updateUserLocation(location)
            }
        }
    }

    private fun checkPermissionsGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if (checkPermissionsGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.also {
                    updateUserLocation(location)
                }
            }
            startLocationUpdate()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        if (checkPermissionsGranted()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun updateUserLocation(location: Location) {
        val currentLocation = UserLocation(
            latitude = location.latitude,
            longitude = location.longitude,
        )
        storeCurrentLocation(currentLocation)
        value = currentLocation
    }

    /**
     * Store current location to Datastore
     **/
    private fun storeCurrentLocation(currentLocation: UserLocation) {
        val locationJson = Gson().toJson(currentLocation)
        HaliYaHewaStore.currentLocation = locationJson
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}
