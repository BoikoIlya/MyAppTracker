package com.ilya.myapptracker.presentation.ui.historyscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeightSettings(
    showAlert: MutableState<Boolean>,
    onSave: ()->Unit,
    weight: MutableState<Float>
){
    val settingsValue =  remember {
        mutableStateOf("")
    }
    settingsValue.value = weight.value.toString()
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
                            weight.value = settingsValue.value.toFloat()
                            onSave()
                        }) {
                        Text(text = "Save", color = Color.Black)
                    }

                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        shape = RoundedCornerShape(20.dp),
                        onClick = {
                            showAlert.value = false

                        }) {
                        Text(text = "Back", color = Color.Black)
                    }
                },
                title = {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Your weight:")
                    }
                },
                text = {
                    TextField(value = settingsValue.value.toString(),
                    onValueChange = {
                     settingsValue.value = it
                })  }
            )
        }
    }
