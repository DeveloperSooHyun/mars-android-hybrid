package com.mars.hybrid.aos.core.util

import android.content.Context
import androidx.core.content.edit

/**
 * ======================================================
 * Title      : PreferenceUtil
 * Developer  : 박수현
 * Date       : 2026-02-02
 * Description:
 *  - 앱 Preference 설정
 *
 * History:
 *  - [2026.02.02] PreferenceUtil 설계
 * ======================================================
 */
class PreferenceUtil(context: Context) {
    private val prefs = context.getSharedPreferences("livart_prefs", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) = prefs.edit { putString(key, value) }

    fun getString(key: String, def: String = ""): String = prefs.getString(key, def) ?: def

    fun putBoolean(key: String, value: Boolean) = prefs.edit { putBoolean(key, value) }

    fun getBoolean(key: String, def: Boolean = false): Boolean = prefs.getBoolean(key, def)

    fun clear() = prefs.edit { clear() }
}