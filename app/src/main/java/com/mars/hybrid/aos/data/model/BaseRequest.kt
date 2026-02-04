package com.mars.hybrid.aos.data.model

import com.mars.hybrid.aos.core.extension.toTextBody
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * ======================================================
 * Title      : BaseRequest
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - 서버로 전달되는 Request DTO (Json 으로 변환)
 *
 * History:
 *  - [2026.02.04] BaseRequest Initialization
 * ======================================================
 */

/* =========================================================
 * 모바일 버전 체크 DTO
 * ========================================================= */
data class GetMobileVersionRequest(
    val version: String
)

/* =========================================================
 * 이미지 저장 DTO
 * ========================================================= */
data class SaveImageRequest(
    val caralcId: String?,
    val orgFileId: String?,
    val seq: String?,
    val deliNo: String?,
    val imgTypCd: RequestBody,
    val image: MultipartBody.Part
) {
    fun toPartMap(): Map<String, RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        caralcId?.let { map["caralcId"] = it.toTextBody() }
        orgFileId?.let { map["orgFileId"] = it.toTextBody() }
        seq?.let { map["seq"] = it.toTextBody() }
        deliNo?.let { map["deliNo"] = it.toTextBody() }

        return map
    }
}
