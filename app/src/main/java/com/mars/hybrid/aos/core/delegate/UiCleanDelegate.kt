package com.mars.hybrid.aos.core.delegate

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.mars.hybrid.aos.core.util.DeviceUtil

/**
 * ======================================================
 * Title      : UiCleanDelegate
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - Ui 초기화
 *
 * History:
 *  - [2026.02.02] UiCleanDelegate Initialization
 * ======================================================
 */
class UiCleanDelegate {

    private val cleanTasks = mutableListOf<() -> Unit>()
    private var toast: Toast? = null

    /* =========================================================
     * UI 수정
     * ========================================================= */
    fun add(task: () -> Unit) {
        cleanTasks.add(task)
    }

    /* =========================================================
     * UI 초기화
     * ========================================================= */
    fun clean() {
        cleanTasks.forEach { it.invoke() }
        cleanTasks.clear()
    }

    /* =========================================================
     * Toast show/hide
     * ========================================================= */
    fun showToast(context: Context, msg: String) {
        hideToast()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun hideToast() {
        toast?.cancel()
        toast = null
    }

    /* =========================================================
     * 앱 종료
     * ========================================================= */
    fun finishApp(activity: Activity) {
        DeviceUtil.finishApp(activity)
    }

}
