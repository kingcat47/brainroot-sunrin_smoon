package com.example.brainroot.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ScrollSpeed(
    speed: Float,
    onSpeedChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    min: Float = 1f,
    max: Float = 10f
) {
    Column(modifier = modifier) {
        Text(
            text = "Scroll Speed",
            fontSize = 14.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                value = speed,
                onValueChange = onSpeedChange,
                valueRange = min..max,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "%.1f".format(speed),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}