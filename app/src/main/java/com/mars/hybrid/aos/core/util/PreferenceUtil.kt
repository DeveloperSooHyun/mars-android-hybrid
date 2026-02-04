package com.mars.hybrid.aos.core.util

import android.content.Context
import androidx.core.content.edit
import com.mars.hybrid.aos.App

/**
 * ======================================================
 * Title      : PreferenceUtil
 * Developer  : 박수현
 * Date       : 2026-02-02
 * Description:
 *  - 앱 SharedPreferences 설정
 *
 * History:
 *  - [2026.02.02] PreferenceUtil 설계
 * ======================================================
 */
object PreferenceUtil {

    private val prefs by lazy {
        App.instance.getSharedPreferences("mars_prefs", Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) =
        prefs.edit { putString(key, value) }

    fun getString(key: String, def: String = ""): String =
        prefs.getString(key, def) ?: def

    fun clear() = prefs.edit { clear() }
}
