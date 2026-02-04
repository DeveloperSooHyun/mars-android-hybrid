package com.mars.hybrid.aos.feature.network

import com.mars.hybrid.aos.core.common.AppInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ======================================================
 * Title      : ApiClient
 * Developer  : Mars
 * Date       : 2026-02-04
 * Description:
 *  - 서버 통신 Retrofit, OkHttpClient 생성
 *
 * History:
 *  - [2026.02.04] ApiClient Initialization
 * ======================================================
 */
object ApiClient {

    /* =========================================================
     * okHttpClient 초기 설정
     * ========================================================= */
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    /* =========================================================
     * RemoteDataSource 에서 ApiService 주입
     * ========================================================= */
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(AppInfo().getServerUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
