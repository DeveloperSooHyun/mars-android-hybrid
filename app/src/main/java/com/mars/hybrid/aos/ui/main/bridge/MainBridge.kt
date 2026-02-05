package com.mars.hybrid.aos.ui.main.bridge

import android.webkit.JavascriptInterface
import org.json.JSONObject

/**
 * ======================================================
 * Title      : MainBridge
 * Developer  : Mars
 * Date       : 2026-02-05
 * Description:
 *  - 서버 JS 통신
 *
 * History:
 *  - [2026.02.05] MainBridge Initialization
 * ======================================================
 */
class MainBridge(
    private val handler: BridgeHandler
) {

    @JavascriptInterface
    fun postMessage(parameter: String) {
        val json = JSONObject(parameter)

        val mode = json.optString("mode", "")
        val param = json.optJSONObject("param") ?: JSONObject()

        handler.onBridgeMessage(mode, param)
    }

    fun callback(mode: String, param: JSONObject) {
        handler.onBridgeCallback(mode, param)
    }
}


