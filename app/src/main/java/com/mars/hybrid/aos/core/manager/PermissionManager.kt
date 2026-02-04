package com.mars.hybrid.aos.core.manager

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * ======================================================
 * Title      : PermissionManager
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - 앱에서 사용하는 공통 Util 선언
 *
 * History:
 *  - [2026.01.29] PermissionManager Initialization
 * ======================================================
 */
class PermissionManager {

    /* =========================================================
     * 필수 권한 생성
     * ========================================================= */
    @SuppressLint("InlinedApi")
    fun getRequiredPermissions(context: Context): List<String> {
        val sdk = Build.VERSION.SDK_INT
        val list = mutableListOf<String>()

        if (sdk >= Build.VERSION_CODES.S) {
            list += Manifest.permission.BLUETOOTH_SCAN
            list += Manifest.permission.BLUETOOTH_CONNECT
        } else {
            list += Manifest.permission.BLUETOOTH
            list += Manifest.permission.BLUETOOTH_ADMIN
        }

        list += Manifest.permission.CAMERA

        list += if (sdk >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (sdk >= Build.VERSION_CODES.S) {
            list += Manifest.permission.ACCESS_COARSE_LOCATION
        } else {
            list += Manifest.permission.ACCESS_FINE_LOCATION
        }

        list += Manifest.permission.CALL_PHONE

        if (sdk >= Build.VERSION_CODES.TIRAMISU) {
            list += Manifest.permission.POST_NOTIFICATIONS
        }

        return list
    }


    /* =========================================================
     * 권한 거부 시
     * ========================================================= */
    fun getDeniedPermissions(context: Context): List<String> {
        return getRequiredPermissions(context).filter {
            ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
    }

}
