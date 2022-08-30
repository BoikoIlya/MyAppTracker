package com.ilya.myapptracker.data.remote.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.utils.Constants
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocationTrackerImpl @Inject constructor(
    private val context: Context,
):LocationTracker {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val _position = MutableStateFlow<Resource<LatLng>>(Resource.Loading())
    private val position = _position.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    override fun enableLocationTracking() {
        if (checkPermissions()) {
            val request = LocationRequest().apply {
                interval = Constants.LOCATION_REQUEST_INTERVAL
                fastestInterval = 1000L
                priority = 100
            }
            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallBack,
                Looper.getMainLooper()
            )
        } else _position.value = Resource.Error(message = Constants.PERMISSION_ERROR)
    }

    override suspend fun getCurrentLocation(): StateFlow<Resource<LatLng>> {
        return position
    }

    private val locationCallBack = object :LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result?.locations?.let{ locationsList->
                locationsList.forEach{ location->
                    Log.d("tag", "${location.latitude}, ${location.longitude}")
                    _position.value = Resource.Success(LatLng(location.latitude,location.longitude))
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

}







