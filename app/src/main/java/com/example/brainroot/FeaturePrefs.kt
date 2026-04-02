package com.example.brainroot

import android.content.Context

/**
 * 앱 기능 상태를 SharedPreferences에 저장/불러오는 헬퍼 오브젝트.
 *
 * - 저장 위치: 앱 내부 전용 파일 (다른 앱에서 접근 불가, MODE_PRIVATE)
 * - 사용 범위: MainActivity(앱 UI) ↔ FeatureTileService(알림창 타일) 간 상태 공유
 *
 * NOTE: 단순 on/off 값이라 SharedPreferences로 충분하지만,
 *       저장 데이터가 복잡해지면 DataStore(Proto/Preferences) 마이그레이션을 고려할 것.
 */
object FeaturePrefs {

    private const val PREFS_NAME = "feature_prefs"

    // 외부에서 key 문자열을 직접 쓰지 않도록 상수로 노출
    // (OnSharedPreferenceChangeListener의 key 비교에 사용)
    const val KEY_EYE_TRACKING = "eye_tracking"
    const val KEY_AUTO_SCROLL  = "auto_scroll"
    const val KEY_SCROLL_SPEED = "scroll_speed"

    /**
     * SharedPreferences 인스턴스를 반환.
     * MainActivity에서 OnSharedPreferenceChangeListener 등록 시 직접 접근 필요.
     */
    fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // --- Getters (기본값: 기능 꺼짐, 속도 5) ---

    fun isEyeTracking(context: Context) =
        prefs(context).getBoolean(KEY_EYE_TRACKING, false)

    fun isAutoScroll(context: Context) =
        prefs(context).getBoolean(KEY_AUTO_SCROLL, false)

    fun getScrollSpeed(context: Context) =
        prefs(context).getFloat(KEY_SCROLL_SPEED, 5f)

    // --- Setters (apply()로 비동기 저장 → 메인 스레드 블로킹 없음) ---

    fun setEyeTracking(context: Context, enabled: Boolean) =
        prefs(context).edit().putBoolean(KEY_EYE_TRACKING, enabled).apply()

    fun setAutoScroll(context: Context, enabled: Boolean) =
        prefs(context).edit().putBoolean(KEY_AUTO_SCROLL, enabled).apply()

    fun setScrollSpeed(context: Context, speed: Float) =
        prefs(context).edit().putFloat(KEY_SCROLL_SPEED, speed).apply()
}
