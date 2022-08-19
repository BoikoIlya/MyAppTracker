package com.ilya.myapptracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData

@Database(
    entities = [RunningData::class, Coordinate::class],
    version = 1
    )
abstract class RunningDB: RoomDatabase() {

    abstract fun getRunningDao(): RunningDao
}