package com.ilya.myapptracker.presentation.ui.historyscreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.activities.TrackingActivity
import com.ilya.myapptracker.presentation.ui.theme.Orange
import com.ilya.myapptracker.presentation.ui.theme.Typography

@Composable
fun ActionBar(
    context:Context,
    showWeightSettingsAlert: MutableState<Boolean>
){


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Total results",
                style = Typography.h1,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.shoes),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Black
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    showWeightSettingsAlert.value = true
                }
                    .size(35.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            OutlinedButton(
                onClick = {
                    context.startActivity(Intent(context, TrackingActivity::class.java))
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(20.dp)

            ) {
                Text(text = "Run", color = Orange)
            }
        }
    }
}