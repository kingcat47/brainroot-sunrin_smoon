package com.example.brainroot

import android.Manifest
import android.content.ComponentName
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.service.quicksettings.TileService
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.brainroot.screens.HomeScreen
import com.example.brainroot.screens.SettingsScreen
import com.example.brainroot.ui.theme.BrainrootTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var eyeTracking by mutableStateOf(false)
    private var autoScroll by mutableStateOf(false)
    private var scrollSpeed by mutableFloatStateOf(5f)

    // SharedPreferences가 바뀌는 순간 즉시 감지하는 리스너
    private val prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            FeaturePrefs.KEY_EYE_TRACKING -> eyeTracking = FeaturePrefs.isEyeTracking(this)
            FeaturePrefs.KEY_AUTO_SCROLL  -> autoScroll = FeaturePrefs.isAutoScroll(this)
            FeaturePrefs.KEY_SCROLL_SPEED -> scrollSpeed = FeaturePrefs.getScrollSpeed(this)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()

        val permissions = mutableListOf(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        permissionLauncher.launch(permissions.toTypedArray())

        setContent {
            var darkTheme by remember { mutableStateOf(true) }
            BrainrootTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf("home") }
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            onNavigateToSettings = { currentScreen = "settings" },
                            eyeTracking = eyeTracking,
                            onEyeTrackingChange = { updateEyeTracking(it) },
                            autoScroll = autoScroll,
                            onAutoScrollChange = { updateAutoScroll(it) },
                            scrollSpeed = scrollSpeed,
                            onScrollSpeedChange = { updateScrollSpeed(it) }
                        )
                        "settings" -> SettingsScreen(
                            onBack = { currentScreen = "home" },
                            darkTheme = darkTheme,
                            onDarkThemeChange = { darkTheme = it }
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        // 초기값 동기화 + 리스너 등록 (앱이 보이는 동안 실시간 반영)
        eyeTracking = FeaturePrefs.isEyeTracking(this)
        autoScroll = FeaturePrefs.isAutoScroll(this)
        scrollSpeed = FeaturePrefs.getScrollSpeed(this)
        FeaturePrefs.prefs(this).registerOnSharedPreferenceChangeListener(prefsListener)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
        FeaturePrefs.prefs(this).unregisterOnSharedPreferenceChangeListener(prefsListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun updateEyeTracking(value: Boolean) {
        eyeTracking = value
        FeaturePrefs.setEyeTracking(this, value)
    }

    private fun updateAutoScroll(value: Boolean) {
        autoScroll = value
        FeaturePrefs.setAutoScroll(this, value)
        // 앱에서 변경했을 때 알림창 타일도 즉시 업데이트
        TileService.requestListeningState(
            this,
            ComponentName(this, FeatureTileService::class.java)
        )
    }

    private fun updateScrollSpeed(value: Float) {
        scrollSpeed = value
        FeaturePrefs.setScrollSpeed(this, value)
    }
}
