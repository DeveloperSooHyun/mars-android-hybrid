package com.mars.hybrid.aos.core.util

import android.content.Context
import android.graphics.Bitmap
import com.mars.hybrid.aos.core.common.AppInfo
import java.io.File
import java.io.FileOutputStream

/**
 * ======================================================
 * Title      : FileUtil
 * Developer  : Mars
 * Date       : 2026-01-29
 * Description:
 *  - 파일 관련 공통 함수
 *
 *
 * History:
 *  - [2026.01.29] FileUtil Initialization
 * ======================================================
 */
object FileUtil {

    /* =========================================================
     * 파일 다운로드 FULL ADDRESS
     * ========================================================= */
    fun getFullDownloadUrl(endPoint: String): String {
        return "${AppInfo().getServerUrl()}$endPoint"
    }

    /* =========================================================
     * Bitmap 저장
     * ========================================================= */
    fun saveBitmap(
        context: Context,
        fileName: String,
        bitmap: Bitmap
    ) {
        val file = File(context.cacheDir, fileName)

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /* =========================================================
     * 캐시 지우기
     * ========================================================= */
    fun clearCache(context: Context) {
        val rootCacheDir = context.cacheDir.parent?.let { File(it) } ?: return
        if (rootCacheDir.exists()) {
            removeRecursive(rootCacheDir)
        }
    }

    private fun removeRecursive(file: File): Boolean {
        if (file.isDirectory) {
            file.listFiles()?.forEach { child ->
                if (child.name == "shared_prefs") return@forEach
                removeRecursive(child)
            }
        }
        return file.delete()
    }

}