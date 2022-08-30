package com.ilya.myapptracker.di

import com.ilya.myapptracker.domain.repositiry.DBRepository
import com.ilya.myapptracker.domain.repositiry.LocationRepository
import com.ilya.myapptracker.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {


    @Provides
    @Singleton
    fun provideCalculateSpeedUseCase(): CalculateSpeedUseCase
    {
        return CalculateSpeedUseCase()
    }

    @Singleton
    @Provides
    fun provideCalculateDistanceUseCase(
        locationRepository: LocationRepository
    ): CalculateDistanceUseCase
    {
        return CalculateDistanceUseCase(locationRepository)
    }


    @Singleton
    @Provides
    fun provideInsertDataToDBUseCase(
        DBRepository: DBRepository,
        calculateAverageSpeedUseCase: CalculateAverageSpeedUseCase,
        calculateCaloriesUseCase: CalculateCaloriesUseCase
    ): InsertDataToDBUseCase{
        return InsertDataToDBUseCase(
            DBRepository,
            calculateAverageSpeedUseCase,
            calculateCaloriesUseCase
        )
    }


    @Singleton
    @Provides
    fun provideCalculateAvgSpeedUseCase(): CalculateAverageSpeedUseCase{
        return CalculateAverageSpeedUseCase()
    }

@Singleton
    @Provides
    fun provideCalculateCaloriesUseCase(): CalculateCaloriesUseCase{
        return CalculateCaloriesUseCase()
    }

    @Singleton
    @Provides
    fun provideGetSumOfDurationFromDBUseCase(
        DBRepository: DBRepository
    ): GetSumOfDurationFromDBUseCase{
        return GetSumOfDurationFromDBUseCase(DBRepository)
    }

    @Singleton
    @Provides
    fun provideGetSumOfCaloriesFromDBUseCase(
        DBRepository: DBRepository
    ): GetSumOfCaloriesFromDBUseCase{
        return GetSumOfCaloriesFromDBUseCase(DBRepository)
    }

    @Singleton
    @Provides
    fun provideGetSumOfDistanceFromDBUseCase(
        DBRepository: DBRepository
    ): GetSumOfDistanceFromDBUseCase{
        return GetSumOfDistanceFromDBUseCase(DBRepository)
    }

}