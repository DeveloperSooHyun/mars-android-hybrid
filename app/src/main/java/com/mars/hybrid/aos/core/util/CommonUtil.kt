package com.mars.hybrid.aos.core.util

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ======================================================
 * Title      : CommonUtil
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - 오늘 일자, jsBridge 등 공통 함수 관리
 *
 * History:
 *  - [2026.02.02] CommonUtil Initialization
 * ======================================================
 */
object CommonUtil {

    /* =========================================================
     * 오늘 일자
     * ========================================================= */
    fun today(format: String): String =
        SimpleDateFormat(format, Locale.KOREA).format(Date())

    /* =========================================================
     * JS Bridge 공통 설정
     * ========================================================= */
    fun jsCallback(mode: String, param: JSONObject?): String =
        JSONObject().apply {
            put("mode", mode)
            param?.let { put("param", it) }
        }.toString()
}