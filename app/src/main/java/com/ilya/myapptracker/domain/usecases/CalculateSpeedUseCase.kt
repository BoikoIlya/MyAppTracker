package com.ilya.myapptracker.domain.usecases

import android.location.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.sql.Time
import kotlin.time.Duration.Companion.seconds

class CalculateSpeedUseCase {

    operator fun invoke(newPosition: LatLng, oldPosition: LatLng): Flow<Float> {
        return flow {
           val distance = FloatArray(1)
            Location.distanceBetween(
                oldPosition.latitude,
                oldPosition.longitude,
                newPosition.latitude,
                newPosition.longitude,
                distance
            )
            val speed = (distance[0]/8f)*3.6f
            emit(speed)
        }
    }
}