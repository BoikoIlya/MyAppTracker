package com.ilya.myapptracker.presentation.ui.detailsscreen


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
import com.ilya.myapptracker.data.local.db.RunningDataAndCoordinates
import com.ilya.myapptracker.domain.model.toLatLng
import com.ramcosta.composedestinations.annotation.Destination

@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@Composable
fun DetailsScreen(
    runningDataAndCoordinates: RunningDataAndCoordinates
){

    val startPositionState = remember { mutableStateOf(
        runningDataAndCoordinates.coordinates[0].toLatLng()
    )}

    val endPositionState = remember { mutableStateOf(
        runningDataAndCoordinates
            .coordinates[runningDataAndCoordinates.coordinates.lastIndex].toLatLng()
        ) }

    val cameraPositionState = rememberCameraPositionState{
        this.position = CameraPosition.fromLatLngZoom(startPositionState.value, 15f)
    }
    cameraPositionState.position =  CameraPosition.fromLatLngZoom(startPositionState.value, 15f)

    val startMarkerPositionState = rememberMarkerState(position = startPositionState.value)
    val endMarkerPositionState = rememberMarkerState(position = endPositionState.value)

    val pointList = remember {
        mutableStateOf(runningDataAndCoordinates.coordinates.map { it.toLatLng() })
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
                state = startMarkerPositionState,
            )
            Marker(
                state = endMarkerPositionState,
            )
            Polyline(
                points = pointList.value,
                color = Color.Blue,
                width = 20f
            )
        }
        BottomBarDetailsScreen(
            runningDataAndCoordinates.runningData.averageSpeedInKmH,
            runningDataAndCoordinates.runningData.calories,
            runningDataAndCoordinates.runningData.distanceInMeters,
            runningDataAndCoordinates.runningData.durationInSeconds,
        )
    }
}

