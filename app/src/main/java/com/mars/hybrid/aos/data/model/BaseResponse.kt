package com.mars.hybrid.aos.data.model

import com.google.gson.annotations.SerializedName

/**
 * ======================================================
 * Title      : BaseResponse
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - 서버로부터 받는 Response DTO (서버 JSON 응답)
 *
 * History:
 *  - [2026.02.04] BaseResponse Initialization
 * ======================================================
 */

/* =========================================================
 * ApiService > Retrofit > RemoteDataSource 에서 사용
 * ========================================================= */
data class BaseResponse<T>(
    val response: T
)

/* =========================================================
 * 모바일 버전 Wrapper
 * ========================================================= */
data class MobileVersionWrapper(
    @SerializedName("common_getMobileVersion")
    val data: MobileVersionResponse
)

/* =========================================================
 * 모바일 버전 Response JSON
 * ========================================================= */
data class MobileVersionResponse(
    val resultCd: String,
    val appUpdateYn: String,
    val appAutoUpdateYn: String,
    val fileName: String?
)

/* =========================================================
 * 이미지 저장 Wrapper
 * ========================================================= */
data class SaveImageWrapper(
    @SerializedName("common_saveImageNative")
    val data: SaveImageResponse
)

/* =========================================================
 * 이미지 저장 Response JSON
 * ========================================================= */
data class SaveImageResponse(
    val resultCd: String,
    val fileId: String?
)
