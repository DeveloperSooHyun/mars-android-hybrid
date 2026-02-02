package com.mars.hybrid.aos.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * ======================================================
 * Title      : NetworkUtil
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - 네트워크 관련 공통 함수
 *
 * History:
 *  - [2026.02.02] NetworkUtil Initialization
 * ======================================================
 */
object NetworkUtil {

    /* =========================================================
     * 네트워크 연결 확인
     * ========================================================= */
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
