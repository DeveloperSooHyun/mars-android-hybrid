package com.mars.hybrid.aos.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.mars.hybrid.aos.BuildConfig

/**
 * ======================================================
 * Title      : AppInfo
 * Developer  : Mars
 * Date       : 2026-02-03
 * Description:
 *  - 앱 정보
 *
 * History:
 *  - [2026.02.03] AppInfo Initialization
 * ======================================================
 */
class AppInfo {

    /* =========================================================
     * 현재 서버 URL 가져오기
     * ========================================================= */
    fun getServerUrl(): String = BuildConfig.SERVER_URL

    /* =========================================================
     * 다운로드 URL 가져오기
     * ========================================================= */
    fun getDownloadUrl(): String = BuildConfig.SERVER_URL + "/app"

    /* =========================================================
     * 현재 APP 버전 가져오기
     * ========================================================= */
    fun getAppVersion(): String = BuildConfig.VERSION_NAME

    /* =========================================================
     * 디바이스 고유 번호
     * ========================================================= */
    @SuppressLint("HardwareIds")
    fun getDeviceUUID(context: Context): String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ).trim()

}