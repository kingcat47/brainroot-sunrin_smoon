package com.example.brainroot

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * 알림창 Quick Settings에 노출되는 Auto Scroll 토글 타일.
 *
 * 등록 방법: AndroidManifest.xml에 BIND_QUICK_SETTINGS_TILE 권한으로 선언.
 * 추가 방법: 사용자가 알림창 편집에서 직접 타일을 패널에 추가해야 함.
 *
 * 상태 흐름:
 *   타일 탭 → SharedPreferences 저장 → syncTileState()로 타일 UI 갱신
 *   앱에서 변경 → TileService.requestListeningState() 호출 → onStartListening() → syncTileState()
 */
class FeatureTileService : TileService() {

    /**
     * 타일이 화면에 표시되는 동안 호출됨 (알림창 열릴 때마다).
     * 앱에서 값을 바꿨을 경우에도 여기서 최신 상태를 반영.
     */
    override fun onStartListening() {
        super.onStartListening()
        syncTileState()
    }

    /**
     * 사용자가 타일을 탭했을 때 호출됨.
     * 현재 값을 반전시켜 저장하고 타일 UI를 즉시 갱신.
     */
    override fun onClick() {
        super.onClick()
        val newValue = !FeaturePrefs.isAutoScroll(this)
        FeaturePrefs.setAutoScroll(this, newValue)
        syncTileState()
    }

    /**
     * SharedPreferences의 현재 값을 읽어 타일의 시각적 상태를 업데이트.
     *
     * STATE_ACTIVE   → 타일 강조 표시 (켜짐)
     * STATE_INACTIVE → 타일 흐리게 표시 (꺼짐)
     * subtitle       → Android 10(Q) 이상에서만 표시됨
     */
    private fun syncTileState() {
        val tile = qsTile ?: return  // 타일이 패널에 없으면 null → 조용히 무시
        val enabled = FeaturePrefs.isAutoScroll(this)

        tile.state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = if (enabled) "켜짐" else "꺼짐"
        }

        tile.updateTile()  // 변경 사항을 시스템에 반영 (호출 누락 시 UI 미갱신)
    }
}
