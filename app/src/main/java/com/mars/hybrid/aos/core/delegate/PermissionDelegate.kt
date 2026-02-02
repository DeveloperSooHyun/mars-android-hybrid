package com.mars.hybrid.aos.core.delegate

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.mars.hybrid.aos.core.common.Constants
import com.mars.hybrid.aos.core.manager.PermissionManager

/**
 * ======================================================
 * Title      : PermissionDelegate
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - 권한 체크
 *  - PermissionManager 와 연결
 *
 * History:
 *  - [2026.01.29] PermissionDelegate Initialization
 * ======================================================
 */
class PermissionDelegate(
    private val activity: Activity
) {

    /* =========================================================
    * 필수 권한 체크
    * ========================================================= */
    fun requestIfNeeded(onAllGranted: () -> Unit) {
        val denied = PermissionManager().getDeniedPermissions(activity)

        if (denied.isEmpty()) {
            onAllGranted()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                denied.toTypedArray(),
                Constants.PERMISSION_CODE
            )
        }
    }

    /* =========================================================
    * 권한 결과
    * ========================================================= */
    fun onRequestPermissionsResult(
        permissions: Array<out String>,
        grantResults: IntArray,
        onDenied: () -> Unit,
        onAllGranted: () -> Unit
    ) {
        val denied = permissions.indices.filter {
            grantResults[it] == PackageManager.PERMISSION_DENIED
        }

        if (denied.isNotEmpty()) onDenied()
        else onAllGranted()
    }

}
