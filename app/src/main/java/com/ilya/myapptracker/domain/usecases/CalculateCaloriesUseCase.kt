package com.ilya.myapptracker.domain.usecases

class CalculateCaloriesUseCase(

) {

    operator fun invoke(avgSpeed:Float, weight: Float = 65.5f, timeInSeconds:Long):Int{
        val timeInMinutes = (timeInSeconds.toFloat()/60)
        val caloriesPerMinute = (((((18f*avgSpeed)-20f)*weight).toInt())/1000)
        val caloriesForAllTime = caloriesPerMinute*timeInMinutes
    return caloriesForAllTime.toInt()
    }
}