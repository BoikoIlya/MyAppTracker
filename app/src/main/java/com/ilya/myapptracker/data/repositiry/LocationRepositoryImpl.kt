package com.ilya.myapptracker.data.repositiry

import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.data.remote.location.LocationTracker
import com.ilya.myapptracker.domain.repositiry.LocationRepository
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.StateFlow

class LocationRepositoryImpl(
    private val locationTracker: LocationTracker
): LocationRepository {

    override fun enableLocationTracking() {
        locationTracker.enableLocationTracking()
    }

    override suspend fun getCurrentLocation(): StateFlow<Resource<LatLng>> {
        return locationTracker.getCurrentLocation()
    }
}