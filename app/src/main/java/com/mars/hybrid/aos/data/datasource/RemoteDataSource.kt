package com.mars.hybrid.aos.data.datasource

import com.mars.hybrid.aos.data.model.GetMobileVersionRequest
import com.mars.hybrid.aos.data.model.MobileVersionResponse
import com.mars.hybrid.aos.data.model.SaveImageRequest
import com.mars.hybrid.aos.data.model.SaveImageResponse
import com.mars.hybrid.aos.feature.network.ApiService

/**
 * ======================================================
 * Title      : RemoteDataSource
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - 서버 통신, ApiService 호출
 *
 * History:
 *  - [2026.02.04] RemoteDataSource Initialization
 * ======================================================
 */
class RemoteDataSource(
    private val api: ApiService
) {

    /* =========================================================
     * 모바일 버전 체크
     * ========================================================= */
    suspend fun getMobileVersion(version: String): MobileVersionResponse {
        val res = api.getMobileVersion(
            GetMobileVersionRequest(version)
        )
        return res.response.data
    }

    /* =========================================================
     * 이미지 저장
     * ========================================================= */
    suspend fun saveImage(
        request: SaveImageRequest
    ): SaveImageResponse {
        val res = api.saveImageNative(
            image = request.image,
            imgTypCd = request.imgTypCd,
            params = request.toPartMap()
        )
        return res.response.data
    }
}
