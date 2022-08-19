package com.ilya.myapptracker.presentation.ui.historyscreen

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
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.activities.MainActivity

@Composable
fun DeleteItemAlert(
    onClick:()->Unit,
    showAlert: MutableState<Boolean>
){
    if(showAlert.value){
                AlertDialog(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                    onDismissRequest = {},
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                            shape = RoundedCornerShape(20.dp),
                            onClick = {
                                showAlert.value = false
                                onClick()
                            }) {
                            Text(text = "Delete", color = Color.Black)
                        }

                    },
                    dismissButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                            shape = RoundedCornerShape(20.dp),
                            onClick = {
                                showAlert.value = false

                            }) {
                            Text(text = "No, I don`t", color = Color.Black)
                        }
                    },
                    title = {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Are you sure you are going to delete?")
                        }
                    },
                    text = { Text(text = "                         ")  }
                )
            }
        }