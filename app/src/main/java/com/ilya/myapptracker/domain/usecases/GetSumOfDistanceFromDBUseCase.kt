package com.ilya.myapptracker.domain.usecases

import com.ilya.myapptracker.domain.repositiry.DBRepository
import com.ilya.myapptracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetSumOfDistanceFromDBUseCase @Inject constructor(
    private val DBRepository: DBRepository
) {

    operator fun invoke(): Flow<Resource<Float>> {
        return  flow{
            try {
                DBRepository.getTotalDistance().collect{
                    emit(Resource.Success(data = it))
                }
            }catch (e: Exception){
                emit(Resource.Error(message = "No data"))
            }
        }
    }
}