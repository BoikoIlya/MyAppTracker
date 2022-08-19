package com.ilya.myapptracker.domain.usecases

class CalculateAverageSpeedUseCase {

    operator fun invoke(distance: Float, time: Long): Float {
        return (distance.toInt().toFloat() / time)*3.6f
    }
}