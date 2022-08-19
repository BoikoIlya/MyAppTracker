package com.ilya.myapptracker.domain.usecases

import android.location.Location
import com.ilya.myapptracker.data.local.location.LocationTrackerImpl
import com.ilya.myapptracker.domain.repositiry.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalculateDistanceUseCase @Inject constructor(
       private val locationRepository: LocationRepository
) {

   private val _calculatedDistance = MutableStateFlow(0f)
    val calculatedDistance = _calculatedDistance.asStateFlow()

    operator fun invoke() {

            val distance = FloatArray(1)
            var oldLat = 0.0
            var oldLon = 0.0
            CoroutineScope(Dispatchers.IO).launch {
                locationRepository.getCurrentLocation().collect {
                    it.data?.let { latlng ->
                        if (oldLat != 0.0 && oldLon != 0.0) {
                            Location.distanceBetween(
                                oldLat,
                                oldLon,
                                latlng.latitude,
                                latlng.longitude,
                                distance
                            )
                            _calculatedDistance.value+=distance[0]
                        }
                        oldLat = latlng.latitude
                        oldLon = latlng.longitude
                    }
                }
            }
        }
    }
