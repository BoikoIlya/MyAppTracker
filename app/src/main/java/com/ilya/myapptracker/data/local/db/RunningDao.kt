package com.ilya.myapptracker.data.local.db

import androidx.room.*
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData
import com.ilya.myapptracker.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface RunningDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertRunningDataToDB(runningData: RunningData)

     @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun insertCoordinatesToDB(coordinate: Coordinate)

     @Query("DELETE FROM ${Constants.RUNNING_RESULTS_TABLE} WHERE runningDataId =:id")
     suspend fun deleteFromDB(id: String)

     @Transaction
     @Query("SELECT * FROM ${Constants.RUNNING_RESULTS_TABLE}")
     fun getAllData(): Flow<MutableList<RunningDataAndCoordinates>>

     @Transaction
     @Query("SELECT * FROM ${Constants.RUNNING_RESULTS_TABLE} WHERE runningDataId = :runningDataId")
     fun getItemById(runningDataId: String): Flow<RunningDataAndCoordinates>

     @Query("SELECT SUM(distanceInMeters) FROM ${Constants.RUNNING_RESULTS_TABLE}")
     fun getTotalDistance(): Flow<Float>

     @Query("SELECT SUM(durationInSeconds) FROM ${Constants.RUNNING_RESULTS_TABLE}")
     fun getTotalDuration(): Flow<Long>

     @Query("SELECT SUM(calories) FROM ${Constants.RUNNING_RESULTS_TABLE}")
     fun getTotalCalories(): Flow<Int>
}