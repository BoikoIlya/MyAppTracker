package com.ilya.myapptracker.domain.repositiry

import com.ilya.myapptracker.data.local.db.RunningDataAndCoordinates
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData
import kotlinx.coroutines.flow.Flow

interface DBRepository {

    suspend fun insertRunningDataToDB(runningData: RunningData)

    suspend fun insertCoordinatesToDB(coordinate: Coordinate)

    suspend fun deleteFromDB(id: String)

    fun getAllData(): Flow<MutableList<RunningDataAndCoordinates>>

    fun getItemById(id: String): Flow<RunningDataAndCoordinates>

    fun getTotalDistance(): Flow<Float>

    fun getTotalDuration(): Flow<Long>

    fun getTotalCalories(): Flow<Int>



}