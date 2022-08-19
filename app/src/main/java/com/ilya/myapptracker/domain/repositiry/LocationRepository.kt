package com.ilya.myapptracker.domain.repositiry

import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {

    fun enableLocationTracking()

   suspend fun getCurrentLocation():StateFlow<Resource<LatLng>>
}