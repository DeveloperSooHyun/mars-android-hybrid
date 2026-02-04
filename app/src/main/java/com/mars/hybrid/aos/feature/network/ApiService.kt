package com.mars.hybrid.aos.feature.network

import com.mars.hybrid.aos.data.model.BaseResponse
import com.mars.hybrid.aos.data.model.GetMobileVersionRequest
import com.mars.hybrid.aos.data.model.MobileVersionWrapper
import com.mars.hybrid.aos.data.model.SaveImageWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 * ======================================================
 * Title      : ApiService
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - API 함수 정의 (URL/Request/Response 정의)
 *
 * History:
 *  - [2026.02.04] ApiService Initialization
 * ======================================================
 */
interface ApiService {

    /* =========================================================
     * 모바일 앱 버전 체크
     * ========================================================= */
    @POST("common/getMobileVersion.dt")
    suspend fun getMobileVersion(
        @Body request: GetMobileVersionRequest
    ): BaseResponse<MobileVersionWrapper>

    /* =========================================================
     * 이미지 전송
     * ========================================================= */
    @Multipart
    @POST("common/saveImageNative.dt")
    suspend fun saveImageNative(
        @Part image: MultipartBody.Part,
        @Part("imgTypCd") imgTypCd: RequestBody,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): BaseResponse<SaveImageWrapper>
}

