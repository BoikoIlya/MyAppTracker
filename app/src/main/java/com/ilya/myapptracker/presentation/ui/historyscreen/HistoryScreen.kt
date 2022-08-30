package com.ilya.myapptracker.presentation.ui.historyscreen

import android.content.Context
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ilya.myapptracker.presentation.ui.theme.WhiteGradientEnd
import com.ilya.myapptracker.presentation.ui.theme.WhiteGradientStart
import com.ilya.myapptracker.presentation.viewmodels.HistoryScreenViewModel
import kotlin.time.Duration.Companion.seconds
import com.ilya.myapptracker.presentation.ui.destinations.DetailsScreenDestination
import com.ilya.myapptracker.utils.GetPermissionsUtil
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){
    GetPermissionsUtil()

    val weightSettings = remember {
        mutableStateOf(viewModel.getWeightSettings())
    }

    val showWeightSettingsAlert = remember {
        mutableStateOf(false)
    }
    if(showWeightSettingsAlert.value){
        WeightSettings(
            showAlert = showWeightSettingsAlert,
            onSave = { viewModel.saveWeightSettings(weightSettings.value) },
            weight = weightSettings
        )
    }

    val context = LocalContext.current

    val isGpsEnabled = remember {
       mutableStateOf(true)
    }

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    isGpsEnabled.value =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    if(!isGpsEnabled.value)
        EnableGps(isGpsEnabled = isGpsEnabled) {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

    val totalDurationInSeconds = remember {
        mutableStateOf(viewModel.totalDurationInSeconds.value)
    }
    totalDurationInSeconds.value = viewModel.totalDurationInSeconds.value

    val totalCalories = remember {
        mutableStateOf(viewModel.totalCalories.value)
    }
    totalCalories.value = viewModel.totalCalories.value

    val totalDistanceInMeters = remember {
        mutableStateOf(viewModel.totalDistanceInMeters.value)
    }
    totalDistanceInMeters.value = viewModel.totalDistanceInMeters.value

LazyColumn(
    modifier = Modifier
        .background(
            Brush.verticalGradient(
                listOf(
                    WhiteGradientStart,
                    WhiteGradientEnd
                ),
            )
        )
        .fillMaxSize()
        .padding(20.dp)
){
    item{
        Column {
           ActionBar(context = context, showWeightSettingsAlert = showWeightSettingsAlert)
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = MainAxisAlignment.SpaceBetween,
                crossAxisSpacing = 10.dp
            ){
                TotalResultsItem(
                    name ="Distance",
                    value =  if (totalDistanceInMeters.value.data?.toInt()!! <1000) "${totalDistanceInMeters.value.data!!.toInt()}m"
                             else "${(totalDistanceInMeters.value.data!! /10).toInt().toFloat()/100}km")
                Spacer(modifier = Modifier.width(20.dp))
                TotalResultsItem(
                    name ="Calories",
                    value = totalCalories.value.data.toString()
                )
                Spacer(modifier = Modifier.width(40.dp))
                TotalResultsItem(
                    name ="Duration",
                    value = totalDurationInSeconds.value.data?.seconds.toString()
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
    if(viewModel.allData.value.size ==0) {
        item {
          NoRunningDataItem()
        }
    }
    items(viewModel.allData.value){ item ->
        Spacer(modifier = Modifier.height(10.dp))
        ResultItem(
            valueDate = item.runningData.dateRun,
            valueDuration = item.runningData.durationInSeconds.seconds.toString(),
            onClick = {
                navigator.navigate(DetailsScreenDestination(item))
            },
            onClickMenu = {
                viewModel.deleteItem(item.runningData.runningDataId)
            }
        )
    }
  }
}

