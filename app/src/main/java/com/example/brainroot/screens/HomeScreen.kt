package com.example.brainroot.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainroot.components.ScrollSpeed
import com.example.brainroot.components.Toggle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    eyeTracking: Boolean,
    onEyeTrackingChange: (Boolean) -> Unit,
    autoScroll: Boolean,
    onAutoScrollChange: (Boolean) -> Unit,
    scrollSpeed: Float,
    onScrollSpeedChange: (Float) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "홈",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "설정",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header()
            ActivityContent(
                eyeTracking = eyeTracking,
                onEyeTrackingChange = onEyeTrackingChange,
                autoScroll = autoScroll,
                onAutoScrollChange = onAutoScrollChange,
                scrollSpeed = scrollSpeed,
                onScrollSpeedChange = onScrollSpeedChange
            )
        }
    }
}

@Composable
fun ActivityContent(
    eyeTracking: Boolean,
    onEyeTrackingChange: (Boolean) -> Unit,
    autoScroll: Boolean,
    onAutoScrollChange: (Boolean) -> Unit,
    scrollSpeed: Float,
    onScrollSpeedChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Toggle(
                label = "Eye Tracking",
                checked = eyeTracking,
                onCheckedChange = onEyeTrackingChange
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            Toggle(
                label = "Auto Scroll",
                checked = autoScroll,
                onCheckedChange = onAutoScrollChange
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            ScrollSpeed(
                speed = scrollSpeed,
                onSpeedChange = onScrollSpeedChange
            )
        }
    }
}

@Composable
fun Header() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Brainroot",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "손가락조차 움직이지 않겠다",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
