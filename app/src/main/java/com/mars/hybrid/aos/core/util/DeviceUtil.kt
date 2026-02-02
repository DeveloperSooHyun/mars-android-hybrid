package com.mars.hybrid.aos.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.net.toUri
import com.mars.hybrid.aos.BuildConfig
import java.io.File

/**
 * ======================================================
 * Title      : DeviceUtil
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - 시스템/기기 관련 공통 함수
 *
 * History:
 *  - [2026.02.02] DeviceUtil Initialization
 * ======================================================
 */
object DeviceUtil {

    /* =========================================================
     * 권한 설정 화면 오픈
     * ========================================================= */
    fun openSetting(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = "package:${BuildConfig.APPLICATION_ID}".toUri()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /* =========================================================
     * 브라우저 URL 오픈
     * ========================================================= */
    fun openBrowser(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }

    /* =========================================================
     * 앱 종료
     * ========================================================= */
    fun finishApp(activity: Activity) {
        activity.moveTaskToBack(true)
        activity.finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /* =========================================================
     * 루팅 단말 체크
     * ========================================================= */
    fun isRootedDevice(): Boolean {
        val paths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/system/app/Superuser.apk"
        )
        return paths.any { File(it).exists() }
    }
}
