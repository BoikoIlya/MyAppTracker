package com.ilya.myapptracker.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.domain.repositiry.LocationRepository
import com.ilya.myapptracker.domain.repositiry.SharedPrefRepository
import com.ilya.myapptracker.presentation.ui.states.CurrentLocationState
import com.ilya.myapptracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.ilya.myapptracker.domain.usecases.*
import com.ilya.myapptracker.presentation.service.TrackerService
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class RunningScreenViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val calculateSpeedUseCase: CalculateSpeedUseCase,
    private val insertDataToDBUseCase: InsertDataToDBUseCase,
    private val sharedPrefRepository: SharedPrefRepository
): ViewModel() {

    private val _serviceState = mutableStateOf(false)
    val serviceState: State<Boolean> = _serviceState

    private val _pointList = mutableStateOf(mutableListOf(LatLng(0.0, 0.0)))
    val pointList: State<MutableList<LatLng>> = _pointList

    private val _weightSettings  = mutableStateOf(0f)
    val weightSettings: State<Float>  = _weightSettings

    private val _currentLocationData = mutableStateOf(CurrentLocationState())
    val currentLocationData = _currentLocationData

    private val _time = mutableStateOf(0L)
    val time: State<Long> = _time

    private val _distance = mutableStateOf(0f)
    val distance: State<Float> = _distance

    private val _speed = mutableStateOf(0f)
    val speed: State<Float> = _speed

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading


    init {
        getTrackingStatus()
        if(!_serviceState.value){
            getLocationBeforeStartingService()
        }
        getTrackedPointList()
        gettingLocationFromService()
        getWeightSettings()
    }

    private fun getLocationBeforeStartingService()= viewModelScope.launch {
        locationRepository.enableLocationTracking()
        locationRepository.getCurrentLocation().collect { Resource ->
            when (Resource) {
                is Resource.Success -> {
                    Resource.data?.let { it ->
                        _currentLocationData.value = CurrentLocationState(it)
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun getTrackedPointList() = viewModelScope.launch {
        TrackerService.pointList.collect{
            _pointList.value = it
            _pointList.value.removeAt(0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
   private fun gettingLocationFromService() = viewModelScope.launch {
              TrackerService.currentLocation.collect{
                  _currentLocationData.value =  CurrentLocationState(it)
                  _pointList.value.add(it)
                  if (_pointList.value[0].latitude==0.0 && _pointList.value[0].longitude==0.0){
                      _pointList.value.removeAt(0)
                  }
            }
        }

     fun startTracking(){
         getRunningDuration()
         getRunningDistance()
         if(_pointList.value.size>6 ) {getRunningSpeed()}
     }

    private fun getRunningDuration() = viewModelScope.launch {
       TrackerService.timer.collect{
      _time.value = it
     }
    }

    private fun getRunningDistance() = viewModelScope.launch {
        TrackerService.distance.collect{
            _distance.value = it
        }
    }

    private fun getRunningSpeed() = viewModelScope.launch{
            calculateSpeedUseCase.invoke(
                _pointList.value[_pointList.value.lastIndex],
                _pointList.value[_pointList.value.lastIndex - 5],
            ).collect {
                _speed.value = it
            }
        }
    private fun getTrackingStatus() = viewModelScope.launch {
        TrackerService.isTracking.collect{
            _serviceState.value = it
        }
    }

     fun insertDataToDB() =viewModelScope.launch {
       insertDataToDBUseCase.inserting(
           _time.value,
           _distance.value,
           _pointList.value,
       _weightSettings.value).collect{
           when(it){
               is Resource.Success -> {
                  _loading.value = false
               }
               is Resource.Error -> {}
               is Resource.Loading -> _loading.value = true
           }
       }
    }


    private fun getWeightSettings(){
       _weightSettings.value = sharedPrefRepository.getDataFromSharedPref()
    }

}