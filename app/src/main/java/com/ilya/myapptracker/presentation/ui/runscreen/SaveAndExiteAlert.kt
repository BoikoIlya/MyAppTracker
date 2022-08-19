package com.ilya.myapptracker.presentation.ui.runscreen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ilya.myapptracker.presentation.ui.destinations.HistoryScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.activities.MainActivity

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SaveAndExit(
    SaveAndExit:()->Unit,
    showAlert: MutableState<Boolean>
){
    val context  = LocalContext.current as Activity
    if (showAlert.value) {
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onDismissRequest = {},
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        SaveAndExit()
                        showAlert.value = false
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                    Text(text = "Save and exit", color = Color.Black)
                }

            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        showAlert.value = false
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    }) {
                    Text(text = "Just exit.", color = Color.Black)
                }
            },
            title = {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Do you want to save?")
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .clickable {
                                showAlert.value = false
                            },
                        tint = Color.Black
                    )
                }
                    },
            text = {Text(text = "                         ")}
        )
    }
}