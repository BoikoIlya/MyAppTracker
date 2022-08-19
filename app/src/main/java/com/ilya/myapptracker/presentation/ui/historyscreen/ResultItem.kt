package com.ilya.myapptracker.presentation.ui.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.theme.Typography

@Composable
fun ResultItem(
    valueDate: String,
    valueDuration: String,
    onClick:()-> Unit,
    onClickMenu:()->Unit
){
    val showDeleteItemAlert = remember {
        mutableStateOf(false)
    }
    if (showDeleteItemAlert.value) {
        DeleteItemAlert(
            onClick = onClickMenu,
            showAlert = showDeleteItemAlert)
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                vertical = 5.dp,
                horizontal = 10.dp
            )
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column() {
            Text(
                text = "Date",
                style = Typography.body1,
                color = Color.Gray
            )
            Text(
                text = valueDate,
                style = Typography.h3,
                color = Color.Black
            )
        }
        Row( verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Text(
                    text = "Duration",
                    style = Typography.body1,
                    color = Color.Gray
                )
                Text(
                    text = valueDuration,
                    style = Typography.h3,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier.clickable {
                    showDeleteItemAlert.value = true
                }
            )
        }
    }
}