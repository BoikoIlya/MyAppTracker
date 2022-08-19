package com.ilya.myapptracker.presentation.ui.historyscreen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.PermissionState

@Composable
fun EnableGps(
    isGpsEnabled: MutableState<Boolean>,
    check:()->Unit
){
    if (!isGpsEnabled.value) {
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onDismissRequest = {},
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        check()
                    }) {
                    Text(text = "I have enabled.", color = Color.Black)
                }
            },
            title = { Text(text = "Please enable GPS.") },
            text = { Text(text = "To track location, please enable GPS.") },
        )
    }
}