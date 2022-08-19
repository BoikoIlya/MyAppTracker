package com.ilya.myapptracker.presentation.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.ilya.myapptracker.presentation.ui.runscreen.RunScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         RunScreen()
        }
    }
}

