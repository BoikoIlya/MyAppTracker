package com.ilya.myapptracker.presentation.ui.historyscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ilya.myapptracker.presentation.ui.theme.Typography

@Composable
fun TotalResultsItem(
    name: String,
    value: String
) {
    Column() {
        Text(
            text = name,
            style = Typography.body1,
            color = Color.Gray
        )
        Text(
            text = value,
            style = Typography.h2,
            color = Color.Black
        )
    }
}