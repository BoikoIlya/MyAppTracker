package com.ilya.myapptracker.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionAlert(
    permissionState: PermissionState,
    showDialog:MutableState<Boolean>
){
    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onDismissRequest = {},
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { permissionState.launchPermissionRequest() }) {
                    Text(text = "Go to settings", color = Color.Black)
                }
            },
            title = { Text(text = "Background location permission") },
            text = { Text(text = "If you expect proper app work, you should give permission to track location all the time.") },
        )
    }
}