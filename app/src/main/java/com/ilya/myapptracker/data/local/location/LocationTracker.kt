package com.ilya.myapptracker.data.local.location

import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.StateFlow

interface LocationTracker {

    fun enableLocationTracking()

    suspend fun getCurrentLocation(): StateFlow<Resource<LatLng>>
}