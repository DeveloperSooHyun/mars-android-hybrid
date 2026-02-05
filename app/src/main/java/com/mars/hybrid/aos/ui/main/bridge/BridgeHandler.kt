package com.mars.hybrid.aos.ui.main.bridge

import org.json.JSONObject

/**
 * ======================================================
 * Title      : BridgeHandler
 * Developer  : Mars
 * Date       : 2026-02-05
 * Description:
 *  - bridge, fragment 연결 인터페이스
 *
 * History:
 *  - [2026.02.05] BridgeHandler Initialization
 * ======================================================
 */
interface BridgeHandler {
    fun onBridgeMessage(mode: String, param: JSONObject)
    fun onBridgeCallback(mode: String, param: JSONObject)
}
