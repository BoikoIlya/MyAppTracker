package com.ilya.myapptracker.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData
import com.ilya.myapptracker.domain.repositiry.DBRepository
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class InsertDataToDBUseCase @Inject constructor(
    val DBRepository: DBRepository,
    val calculateAverageSpeedUseCase: CalculateAverageSpeedUseCase,
    val calculateCaloriesUseCase: CalculateCaloriesUseCase
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun inserting(
        time: Long,
        distance: Float,
        pointList: MutableList<LatLng>,
        weightSettings: Float
    ):Flow<Resource<String>>{
      return  flow {
          emit(Resource.Loading())
        val id = UUID.randomUUID().toString()
        val avgSpeed = calculateAverageSpeedUseCase(distance, time)
        val calories = calculateCaloriesUseCase(avgSpeed, timeInSeconds = time, weight = weightSettings)
        val date =  LocalDate.now().toString()
        val runningData = RunningData(
                    runningDataId = id,
                    dateRun =date,
                    distanceInMeters = distance,
                    durationInSeconds = time,
                    averageSpeedInKmH = avgSpeed,
                    calories = calories)

        DBRepository.insertRunningDataToDB(runningData)

        repeat(pointList.size){index->
            DBRepository.insertCoordinatesToDB(
                Coordinate(
                    latitude = pointList[index].latitude,
                    longitude =  pointList[index].longitude,
                    runningDataId =  id
                )
            )
            if (pointList.size==index) emit(Resource.Success("Complete"))
        }

    }
      }
  }
