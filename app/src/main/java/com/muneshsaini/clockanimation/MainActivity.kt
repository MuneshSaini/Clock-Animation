package com.muneshsaini.clockanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            ClockScreen()
        }
    }
}

@Composable
fun ClockScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date Display
        DateDisplay()

        // Clock Animation
        ClockAnimation()

        // Music Player
        MusicPlayer()

        // Battery Percentage
        BatteryPercentage()
    }
}

@Composable
fun DateDisplay() {
    val currentDate = remember {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        "$dayOfWeek $dayOfMonth"
    }

    BasicText(
        text = currentDate,
        style = TextStyle(color = Color(0xFFE91E63), fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun ClockAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val secondsAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val minutesAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val hoursAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 43200000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawCircle(
                brush = Brush.sweepGradient(listOf(Color.Red, Color.Blue, Color.Magenta)),
                style = Stroke(width = 4.dp.toPx())
            )

            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2 - 16.dp.toPx()

            val secondsHandLength = radius - 16.dp.toPx()
            val minutesHandLength = radius - 32.dp.toPx()
            val hoursHandLength = radius - 48.dp.toPx()

            val secondsX = centerX + secondsHandLength * cos((secondsAngle - 90) * PI / 180).toFloat()
            val secondsY = centerY + secondsHandLength * sin((secondsAngle - 90) * PI / 180).toFloat()

            val minutesX = centerX + minutesHandLength * cos((minutesAngle - 90) * PI / 180).toFloat()
            val minutesY = centerY + minutesHandLength * sin((minutesAngle - 90) * PI / 180).toFloat()

            val hoursX = centerX + hoursHandLength * cos((hoursAngle - 90) * PI / 180).toFloat()
            val hoursY = centerY + hoursHandLength * sin((hoursAngle - 90) * PI / 180).toFloat()

            drawLine(
                color = Color.Red,
                start = Offset(centerX, centerY),
                end = Offset(secondsX, secondsY),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color.Cyan,
                start = Offset(centerX, centerY),
                end = Offset(minutesX, minutesY),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color.Magenta,
                start = Offset(centerX, centerY),
                end = Offset(hoursX, hoursY),
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawCircle(
                color = Color.White,
                radius = 8.dp.toPx(),
                center = Offset(centerX, centerY)
            )
        }
    }
}

@Composable
fun MusicPlayer() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 16.dp)) {
        BasicText(
            text = "That Cool Song",
            style = TextStyle(color = Color(0xFFE91E63), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
        BasicText(
            text = "It's Artist",
            style = TextStyle(color = Color(0xFFE91E63), fontSize = 14.sp)
        )
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color(0xFFE91E63))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Filled.PlayArrow, contentDescription = "Next", tint = Color(0xFFE91E63))
        }
    }
}

@Composable
fun BatteryPercentage() {
    BasicText(
        text = "34%",
        style = TextStyle(color = Color(0xFFE91E63), fontSize = 18.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    MyApp()
}