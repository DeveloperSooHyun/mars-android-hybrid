package com.mars.hybrid.aos.feature.network

import com.mars.hybrid.aos.core.util.PreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

/**
 * ======================================================
 * Title      : HeaderInterceptor
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - 공통 API Header 설정
 *
 * History:
 *  - [2026.02.04] HeaderInterceptor Initialization
 * ======================================================
 */
class HeaderInterceptor() : Interceptor {
    /* =========================================================
     * ApiClient 에서 OkHttpClient 생성 시 호출
     * ========================================================= */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Accept-Language", Locale.getDefault().language)
            .addHeader("nativeYn", "Y")
            .addHeader("token", PreferenceUtil.getString("token", ""))
            .build()

        return chain.proceed(request)
    }
}
