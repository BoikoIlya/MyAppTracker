package com.ilya.myapptracker.data.repositiry

import com.ilya.myapptracker.data.local.db.RunningDao
import com.ilya.myapptracker.data.local.db.RunningDataAndCoordinates
import com.ilya.myapptracker.data.local.sharedpref.SharedPrefImpl
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData
import com.ilya.myapptracker.domain.repositiry.DBRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepositoryImpl @Inject constructor(
   private val runningDao: RunningDao,
): DBRepository {

    override suspend fun insertRunningDataToDB(runningData: RunningData) {
        runningDao.insertRunningDataToDB(runningData)
    }

    override suspend fun insertCoordinatesToDB(coordinate: Coordinate) {
       runningDao.insertCoordinatesToDB(coordinate)
    }

    override suspend fun deleteFromDB(id: String) {
        runningDao.deleteFromDB(id)
    }

    override fun getAllData(): Flow<MutableList<RunningDataAndCoordinates>> {
        return runningDao.getAllData()
    }

    override fun getItemById(id: String): Flow<RunningDataAndCoordinates> {
       return runningDao.getItemById(id)
    }

    override fun getTotalDistance(): Flow<Float> {
        return runningDao.getTotalDistance()
    }

    override fun getTotalDuration(): Flow<Long> {
        return runningDao.getTotalDuration()
    }

    override fun getTotalCalories(): Flow<Int> {
        return runningDao.getTotalCalories()
    }

}