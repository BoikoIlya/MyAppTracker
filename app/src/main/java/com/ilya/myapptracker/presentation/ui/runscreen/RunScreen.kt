package com.ilya.myapptracker.presentation.ui.runscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.ilya.myapptracker.presentation.viewmodels.RunningScreenViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RunScreen(
    viewModel: RunningScreenViewModel = hiltViewModel(),
){

        val positionState = remember { mutableStateOf(viewModel._currentLocationData.value.data)}
        val cameraPositionState = rememberCameraPositionState{
            this.position = CameraPosition.fromLatLngZoom(positionState.value, 15f)
        }
        val markerPositionState = rememberMarkerState(position = positionState.value)
        val time = viewModel._time
        val distance = viewModel._distance
        val speed = viewModel._speed
        val serviceState =  remember {
            mutableStateOf(viewModel._serviceState.value)
        }

        positionState.value = viewModel._currentLocationData.value.data
        cameraPositionState.position = CameraPosition.fromLatLngZoom(positionState.value, 15f)
        markerPositionState.position = positionState.value


          if (serviceState.value){
              viewModel.startTracking()
          }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.83f),
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                cameraPositionState = cameraPositionState
            ){
                Marker(
                    state = markerPositionState,
                    title = "${positionState.value}",
                )
                Polyline(
                    points = viewModel._pointList.value.toList(),
                    color = Color.Blue,
                    width = 20f
                )
            }
            BottomBar(time, distance, speed, serviceState)
        }
    }

