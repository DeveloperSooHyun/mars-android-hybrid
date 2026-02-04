package com.mars.hybrid.aos.data.repository

import com.mars.hybrid.aos.data.datasource.RemoteDataSource
import com.mars.hybrid.aos.data.model.MobileVersionResponse
import com.mars.hybrid.aos.data.model.SaveImageRequest
import com.mars.hybrid.aos.data.model.SaveImageResponse

/**
 * ======================================================
 * Title      : AppRepository
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - ViewModel 과 DataSource 사이의 중간
 *
 * History:
 *  - [2026.02.04] AppRepository Initialization
 * ======================================================
 */
class AppRepository(
    private val remote: RemoteDataSource
) {
    /* =========================================================
     * 앱 버전 체크 호출
     * ========================================================= */
    suspend fun checkAppVersion(version: String): MobileVersionResponse {
        return remote.getMobileVersion(version)
    }

    /* =========================================================
     * 이미지 저장 호출
     * ========================================================= */
    suspend fun saveImage(request: SaveImageRequest): SaveImageResponse {
        return remote.saveImage(request)
    }
}
