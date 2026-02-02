package com.mars.hybrid.aos

import android.app.Application

/**
 * ======================================================
 * Title      : App
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - 전역 초기화 및 아키텍처 루트
 *
 * History:
 *  - [2026.01.29] App Initialization
 * ======================================================
 */
class App : Application() {

    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
