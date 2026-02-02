package com.mars.hybrid.aos.core.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mars.hybrid.aos.core.delegate.BackPressDelegate
import com.mars.hybrid.aos.core.manager.DeviceCheckManager
import com.mars.hybrid.aos.ui.common.DeviceErrorHandler
import androidx.activity.OnBackPressedCallback
import com.mars.hybrid.aos.core.delegate.DialogDelegate
import com.mars.hybrid.aos.core.delegate.UiCleanDelegate
import com.mars.hybrid.aos.core.manager.PermissionManager
import com.mars.hybrid.aos.core.util.FileUtil

/**
 * ======================================================
 * Title      : BaseActivity
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - UI 공통 설정
 *  - delegate 연결점
 *
 * History:
 *  - [2026.01.29] BaseActivity Initialization
 * ======================================================
 */
open class BaseActivity : AppCompatActivity() {

    private lateinit var backPressDelegate: BackPressDelegate
    private lateinit var deviceCheckManager: DeviceCheckManager
    private lateinit var deviceErrorHandler: DeviceErrorHandler
    private lateinit var dialogDelegate: DialogDelegate
    private lateinit var permissionManager: PermissionManager

    val uiCleanDelegate = UiCleanDelegate()

    /* =========================================================
     * Activity 생명주기
     * ========================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Delegate & Manager 초기화
        permissionManager = PermissionManager()
        dialogDelegate = DialogDelegate(this, uiCleanDelegate)
        deviceCheckManager = DeviceCheckManager(this, permissionManager)
        deviceErrorHandler = DeviceErrorHandler(this, dialogDelegate)

        // 환경 체크
        if (!checkDeviceStatus(checkPermission = true)) return

        // 화면 세팅
        setupWindowOptions()
        setupBackPressedHandler()

        FileUtil.clearCache(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        uiCleanDelegate.clean()
    }

    /* =========================================================
     * 초기 화면 해상도 설정
     * ========================================================= */
    private fun setupWindowOptions() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /* =========================================================
     * 뒤로가기 이벤트
     * ========================================================= */
    private fun setupBackPressedHandler() {
        backPressDelegate = BackPressDelegate(this)

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressDelegate.handleDoubleBackPress()
                }
            }
        )
    }

    /* =========================================================
     * 디바이스 상태 체크
     * ========================================================= */
    protected fun checkDeviceStatus(checkPermission: Boolean): Boolean {
        val result = deviceCheckManager.check(checkPermission)

        if (!result.isValid) {
            deviceErrorHandler.handle(result)
            return false
        }
        return true
    }
}
