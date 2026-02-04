package com.mars.hybrid.aos.core.delegate

import android.app.Activity
import android.content.Intent
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.common.DeviceCheckResult
import com.mars.hybrid.aos.core.manager.DeviceErrorType
import com.mars.hybrid.aos.ui.error.ErrorActivity
import com.mars.hybrid.aos.ui.permission.PermissionActivity

/**
 * ======================================================
 * Title      : DeviceErrorHandler
 * Developer  : Mars
 * Date       : 2026-01-30
 * Description:
 *  - 기기 상태 오류 시 DialogDelegate 이용하여 알림
 *
 * History:
 *  - [2026.01.30] DeviceErrorHandler Initialization
 * ======================================================
 */
class DeviceErrorHandler(
    private val activity: Activity,
    private val dialogDelegate: DialogDelegate
) {

    /* =========================================================
     * 기기 상태 이상 시 컨트롤
     * ========================================================= */
    fun handle(result: DeviceCheckResult) {

        if (result.isValid) return

        dialogDelegate.showCustomDialog(
            title = activity.getString(R.string.title_dialog),
            message = result.message,
            isConfirm = false,
            onPositive = {
                when (result.type) {
                    DeviceErrorType.ROOT -> {
                        activity.finish()
                    }

                    DeviceErrorType.NETWORK -> {
                        val intent = Intent(activity, ErrorActivity::class.java)
                        activity.startActivity(intent)
                    }

                    DeviceErrorType.PERMISSION -> {
                        val intent = Intent(activity, PermissionActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        activity.startActivity(intent)
                    }

                    else -> {}
                }
            }
        )
    }
}