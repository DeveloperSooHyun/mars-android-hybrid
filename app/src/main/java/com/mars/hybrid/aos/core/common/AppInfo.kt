package com.mars.hybrid.aos.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.mars.hybrid.aos.BuildConfig

class AppInfo {

    fun getServerUrl(): String = when (BuildConfig.FLAVOR) {
        "local" -> "http://192.168.0.75:8080"
        "dev" -> "http://192.168.0.75:8080"
        else -> "https://tmsweb.casamia.co.kr"
    }

    fun getAppVersion(): String = BuildConfig.VERSION_NAME

    @SuppressLint("HardwareIds")
    fun getDeviceUUID(context: Context): String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ).trim()

}