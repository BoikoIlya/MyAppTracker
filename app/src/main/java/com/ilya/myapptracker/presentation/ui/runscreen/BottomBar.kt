package com.ilya.myapptracker.presentation.ui.runscreen

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.service.TrackerService
import com.ilya.myapptracker.presentation.ui.detailsscreen.StatisticItem
import com.ilya.myapptracker.presentation.ui.theme.Orange
import com.ilya.myapptracker.presentation.ui.theme.Typography
import com.ilya.myapptracker.presentation.viewmodels.RunningScreenViewModel
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomBar(
    time: State<Long>,
    distance: State<Float>,
    speed: State<Float>,
    serviceState: MutableState<Boolean>,
    viewModel: RunningScreenViewModel = hiltViewModel(),
)
{
    val context = LocalContext.current as Activity

    val saveBtnVisibility = remember {
        mutableStateOf(false)
    }

    val showSaveAlert = remember {
        mutableStateOf(false)
    }
    if (showSaveAlert.value){
        SaveAndExit(
            SaveAndExit = {
                viewModel.insertDataToDB()
                if (!viewModel.loading.value)
                    context.finish()
                 TrackerService.clearData()
                          },
            showAlert = showSaveAlert)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = "Your route",
                   style = Typography.h4
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.shoes),
                    contentDescription = "",
                    Modifier.size(30.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter =  painterResource(id = R.drawable.ic_save),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (saveBtnVisibility.value) showSaveAlert.value = true
                        }
                        .size(45.dp),
                    tint = if (saveBtnVisibility.value) Color.Black
                           else Color.White
                )
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        serviceState.value = !serviceState.value
                        if(serviceState.value){
                            context.startForegroundService(Intent(context, TrackerService::class.java))
                        }else context.stopService(Intent(context, TrackerService::class.java))
                        saveBtnVisibility.value = true
                              },
                    colors = ButtonDefaults.buttonColors(Orange)
                ) {
                    Icon(
                        painter = if (serviceState.value) painterResource(id = R.drawable.ic_pause)
                        else painterResource(id = R.drawable.ic_start),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(5.dp))
        Row(
           modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticItem(name = "Distance", value = if (distance.value.toInt()<1000) "${distance.value.toInt()}m"
            else "${(distance.value/10).toInt().toFloat()/100}km")
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Time", value = time.value.seconds.toString())
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Speed", value = "${((speed.value*10).toInt().toFloat()/10)}km/h")
        }
    }
}
