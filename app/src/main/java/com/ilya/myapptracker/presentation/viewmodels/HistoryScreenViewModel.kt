package com.ilya.myapptracker.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.myapptracker.data.local.db.RunningDataAndCoordinates
import com.ilya.myapptracker.domain.model.RunningData
import com.ilya.myapptracker.domain.repositiry.DBRepository
import com.ilya.myapptracker.domain.repositiry.SharedPrefRepository
import com.ilya.myapptracker.domain.usecases.GetSumOfCaloriesFromDBUseCase
import com.ilya.myapptracker.domain.usecases.GetSumOfDistanceFromDBUseCase
import com.ilya.myapptracker.domain.usecases.GetSumOfDurationFromDBUseCase
import com.ilya.myapptracker.presentation.ui.states.TotalCaloriesFromDBState
import com.ilya.myapptracker.presentation.ui.states.TotalDistanceFromDBState
import com.ilya.myapptracker.presentation.ui.states.TotalTimeFromDBState
import com.ilya.myapptracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val DBRepository: DBRepository,
    private val sharedPrefRepository: SharedPrefRepository,
    private val getSumOfDurationFromDBUseCase: GetSumOfDurationFromDBUseCase,
    private val getSumOfCaloriesFromDBUseCase: GetSumOfCaloriesFromDBUseCase,
    private val getSumOfDistanceFromDBUseCase: GetSumOfDistanceFromDBUseCase
): ViewModel() {

    private val _allData = mutableStateOf(mutableListOf(RunningDataAndCoordinates(runningData = RunningData(
        runningDataId = "",
        dateRun = "",
        distanceInMeters = 0.0f,
        durationInSeconds = 0,
        averageSpeedInKmH = 0.0f,
        calories = 0), emptyList() )))
    val allData: State<MutableList<RunningDataAndCoordinates>> = _allData

    private val _totalDurationInSeconds = mutableStateOf(TotalTimeFromDBState())
    val totalDurationInSeconds: State<TotalTimeFromDBState> = _totalDurationInSeconds

    private val _totalCalories = mutableStateOf(TotalCaloriesFromDBState())
    val totalCalories: State<TotalCaloriesFromDBState> = _totalCalories

    private val _totalDistanceInMeters = mutableStateOf(TotalDistanceFromDBState())
    val totalDistanceInMeters: State<TotalDistanceFromDBState> = _totalDistanceInMeters


    init {
        getAllData()
        getTotalCalories()
        getTotalDistanceInMeters()
        getTotalDurationInSeconds()
    }

   private fun getAllData() = viewModelScope.launch{
        DBRepository.getAllData().collect{ data->
            _allData.value = data
        }
    }

    private fun getTotalDurationInSeconds() = viewModelScope.launch {
       getSumOfDurationFromDBUseCase().collect{
          _totalDurationInSeconds.value =  when(it){
               is Resource.Success->  TotalTimeFromDBState(it.data)
               is Resource.Error->  TotalTimeFromDBState(error = it.message)
              is Resource.Loading -> TODO()
          }
        }
    }

    private fun getTotalCalories() = viewModelScope.launch {
        getSumOfCaloriesFromDBUseCase().collect{
            _totalCalories.value =  when(it){
                is Resource.Success->  TotalCaloriesFromDBState(it.data)
                is Resource.Error->  TotalCaloriesFromDBState(error = it.message)
                is Resource.Loading -> TODO()
            }
        }
    }

    private fun getTotalDistanceInMeters() = viewModelScope.launch {
        getSumOfDistanceFromDBUseCase().collect{
            _totalDistanceInMeters.value =  when(it){
                is Resource.Success->  TotalDistanceFromDBState(it.data)
                is Resource.Error->  TotalDistanceFromDBState(error = it.message)
                is Resource.Loading -> TODO()
            }
        }
    }

    fun deleteItem(id: String) = viewModelScope.launch {
      DBRepository.deleteFromDB(id)
    }

    fun getWeightSettings(): Float{
        return sharedPrefRepository.getDataFromSharedPref()
    }

    fun saveWeightSettings(weight: Float){
        sharedPrefRepository.putDataInSharedPref(weight)
    }
}