package com.ilya.myapptracker.presentation.ui.detailsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomBarDetailsScreen(
   speed: Float,
   calories: Int,
   distance: Float,
   time: Long
)
{

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .fillMaxWidth()

    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = "Your results",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.shoes),
                    contentDescription = "",
                    Modifier.size(30.dp)
                )
            }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Distance", value = if (distance.toInt()<1000) "${distance.toInt()} m"
            else "${(distance/10).toInt().toFloat()/100} km")
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Time", value = time.seconds.toString())
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Avg Speed", value = "${((speed*10).toInt().toFloat()/10)} km/h")
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            StatisticItem(name = "Calories", value = "$calories kcal")
        }
    }


