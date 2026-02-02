package com.mars.hybrid.aos.core.manager

import android.content.Context
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.common.DeviceCheckResult
import com.mars.hybrid.aos.core.util.DeviceUtil
import com.mars.hybrid.aos.core.util.NetworkUtil

/**
 * ======================================================
 * Title      : DeviceCheckManager
 * Developer  : Mars
 * Date       : 2026-01-30
 * Description:
 *  - 기기 상태 분기 처리
 *
 * History:
 *  - [2026.01.30] DeviceCheckManager Initialization
 * ======================================================
 */
class DeviceCheckManager(
    private val context: Context,
    private val permissionManager: PermissionManager
) {

    /* =========================================================
     * 기기 상태 확인
     * ========================================================= */
    fun check(checkPermission: Boolean): DeviceCheckResult {

        return when {
            DeviceUtil.isRootedDevice() ->
                DeviceCheckResult(
                    false,
                    DeviceErrorType.ROOT,
                    context.getString(R.string.msg_rooted_device)
                )

            !NetworkUtil.isConnected(context) ->
                DeviceCheckResult(
                    false,
                    DeviceErrorType.NETWORK,
                    context.getString(R.string.msg_required_network)
                )

            checkPermission &&
                    permissionManager.getDeniedPermissions(context).isNotEmpty() ->
                DeviceCheckResult(
                    false,
                    DeviceErrorType.PERMISSION,
                    context.getString(R.string.msg_required_permission)
                )

            else -> DeviceCheckResult(true)
        }
    }
}

/* =========================================================
 * 에러 유형 정의
 * ========================================================= */
enum class DeviceErrorType {
    ROOT,
    NETWORK,
    PERMISSION
}
