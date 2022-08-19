@file:OptIn(ExperimentalPermissionsApi::class)


package com.ilya.myapptracker.presentation.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.google.accompanist.permissions.*
import com.ilya.myapptracker.presentation.ui.NavGraphs
import com.ilya.myapptracker.presentation.ui.theme.MyAppTrackerTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTrackerTheme {
               DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }



}
















