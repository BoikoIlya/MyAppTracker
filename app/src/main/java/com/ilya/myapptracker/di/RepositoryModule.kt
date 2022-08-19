package com.ilya.myapptracker.di

import android.content.Context
import androidx.room.Room
import com.ilya.myapptracker.data.local.db.RunningDB
import com.ilya.myapptracker.data.local.db.RunningDao
import com.ilya.myapptracker.data.local.location.LocationTracker
import com.ilya.myapptracker.data.local.location.LocationTrackerImpl
import com.ilya.myapptracker.data.local.sharedpref.SharedPref
import com.ilya.myapptracker.data.local.sharedpref.SharedPrefImpl
import com.ilya.myapptracker.data.repositiry.DBRepositoryImpl
import com.ilya.myapptracker.data.repositiry.LocationRepositoryImpl
import com.ilya.myapptracker.data.repositiry.SharedPrefRepositoryImpl
import com.ilya.myapptracker.domain.repositiry.DBRepository
import com.ilya.myapptracker.domain.repositiry.LocationRepository
import com.ilya.myapptracker.domain.repositiry.SharedPrefRepository
import com.ilya.myapptracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationTracker(
        @ApplicationContext
        context: Context,
    ): LocationTracker {
        return LocationTrackerImpl(context)
    }

    @Provides
    @Singleton
    fun provideRunningDB(
        @ApplicationContext
        context: Context
    ): RunningDB {
        return Room.databaseBuilder(
            context,
            RunningDB::class.java,
            Constants.RUNNING_RESULTS_TABLE
        ).build()
    }

    @Provides
    @Singleton
    fun provideRunningDao(
        runningDB: RunningDB
    ): RunningDao {
        return runningDB.getRunningDao()
    }

    @Provides
    @Singleton
    fun provideDBRepository(
        runningDao: RunningDao,
    ): DBRepository{
        return DBRepositoryImpl(runningDao)
    }

    @Provides
    @Singleton
    fun provideShredPref(
        @ApplicationContext
        context: Context
    ): SharedPref
    {
        return SharedPrefImpl(context)
    }

    @Provides
    @Singleton
    fun provideShredPrefRepository(
        sharedPref: SharedPref
    ): SharedPrefRepository
    {
        return SharedPrefRepositoryImpl(sharedPref)
    }


    @Provides
    @Singleton
    fun provideLocationTrackerRepository(
        locationTracker: LocationTracker
    ): LocationRepository
    {
        return LocationRepositoryImpl(locationTracker)
    }
}