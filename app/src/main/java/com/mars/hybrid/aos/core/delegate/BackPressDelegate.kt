package com.mars.hybrid.aos.core.delegate

import android.app.Activity
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.util.CommonUtil

/**
 * ======================================================
 * Title      : BackPressDelegate
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - 뒤로가기 액션
 *
 * History:
 *  - [2026.01.29] BackPressDelegate Initialization
 * ======================================================
 */
class BackPressDelegate(
    private val activity: Activity
) {
    private var lastBackPressedTime = 0L
    private val interval = 2000L

    /* =========================================================
    * 두번 뒤로가기 클릭 이벤트
    * ========================================================= */
    fun handleDoubleBackPress() {
        val now = System.currentTimeMillis()
        if (now - lastBackPressedTime < interval) {
            CommonUtil.finishApp(activity)
        } else {
            CommonUtil.toastShow(
                activity,
                activity.getString(R.string.app_finish_again)
            )
            lastBackPressedTime = now
        }
    }
}
