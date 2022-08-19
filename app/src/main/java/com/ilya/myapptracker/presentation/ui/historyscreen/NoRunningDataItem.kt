package com.ilya.myapptracker.presentation.ui.historyscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.theme.Typography

@Composable
fun NoRunningDataItem(){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_face) ,
            contentDescription ="",
            modifier = Modifier.size(100.dp),)
        Text(
            text = "You haven`t run once!",
            style = Typography.h1,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }

}