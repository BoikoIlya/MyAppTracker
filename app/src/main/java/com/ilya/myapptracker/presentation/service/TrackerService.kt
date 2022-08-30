package com.ilya.myapptracker.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.data.remote.location.LocationTracker
import com.ilya.myapptracker.domain.usecases.CalculateDistanceUseCase
import com.ilya.myapptracker.utils.Constants
import com.ilya.myapptracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
 class TrackerService :LifecycleService() {

    companion object{
        private val _pointList = MutableStateFlow(mutableListOf(LatLng(0.0, 0.0)))
         val pointList = _pointList.asStateFlow()
        private val _currentLocation = MutableStateFlow(LatLng(0.0, 0.0))
        val currentLocation = _currentLocation.asStateFlow()
        private val _timer = MutableStateFlow(0L)
         val timer = _timer.asStateFlow()
        private val _distance = MutableStateFlow(0f)
         val distance = _distance.asStateFlow()
        private val _isTracking =  MutableStateFlow(false)
         val isTracking =  _isTracking.asStateFlow()

        fun clearData(){
            _pointList.value =mutableListOf(LatLng(0.0, 0.0))
            _timer.value = 0L
            _distance.value = 0f
        }
    }


    @Inject
    lateinit var calculateDistanceUseCase: CalculateDistanceUseCase

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var locationTracker: LocationTracker

    @RequiresApi(Build.VERSION_CODES.O)
    private val notificationChannel = NotificationChannel(
        Constants.NOTIFICATION_CHANNEL_ID,
        Constants.NOTIFICATION_NAME,
        NotificationManager.IMPORTANCE_LOW
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        _isTracking.value = true

        addNewPointToList()
        _pointList.value.removeAt(0)

        this.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(notificationChannel)

        increaseTime()
        calculateDistance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _isTracking.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTracking()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun increaseTime() =
        lifecycleScope.launch {
            while (true) {
                delay(1000L)
                _timer.value++
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTracking() {
        val manager = this.getSystemService(NotificationManager::class.java) as NotificationManager

        startForeground(
            Constants.SERVICE_ID,
            notificationBuilder.build()
        )

        lifecycleScope.launch {
            timer.collectLatest{ time->
                distance.collect { latestDistance->

                   val dist: String = if (latestDistance.toInt()<1000) "${latestDistance.toInt()}m"
                    else "${(latestDistance/10).toInt().toFloat()/100}km"

                    val notification = notificationBuilder.setContentText("Duration: ${time.seconds} " +
                            "       Distance: $dist")
                    manager.notify(Constants.NOTIFICATION_ID, notification.build())
                }
            }
        }
    }

    private fun calculateDistance()= lifecycleScope.launch{
              calculateDistanceUseCase()
              calculateDistanceUseCase.calculatedDistance.collect{
                  _distance.value =it
              }
    }

    private fun addNewPointToList() = lifecycleScope.launch {
        locationTracker.getCurrentLocation() .collect{ Resource->
            when (Resource){
                is Resource.Success -> {
                    Resource.data?.let { it ->
                    _currentLocation.value = it
                        _pointList.value.add(it)
                    }
                }
                is Resource.Error-> {}
                is Resource.Loading ->{}
            }
        }
    }
}






