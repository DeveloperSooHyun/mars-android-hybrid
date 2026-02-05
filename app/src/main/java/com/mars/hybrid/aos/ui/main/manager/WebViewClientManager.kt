package com.mars.hybrid.aos.ui.main.manager

import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * ======================================================
 * Title      : WebViewClientManager
 * Developer  : Mars
 * Date       : 2026-02-05
 * Description:
 *  - WebView 페이지 관제 (특정 페이지 추출)
 *
 * History:
 *  - [2026.02.05] WebViewClientManager Initialization
 * ======================================================
 */
class WebViewClientManager(
    private val onSecureChanged: (Boolean) -> Unit
) : WebViewClient() {

    private var isSecurePage = false

    override fun onPageFinished(view: WebView?, url: String?) {
        url ?: return

        val securePages = listOf("OmLoadAccept", "DmDlvy")
        val secure = securePages.any { url.contains(it) }

        if (secure != isSecurePage) {
            isSecurePage = secure
            onSecureChanged(secure)
        }
    }
}

