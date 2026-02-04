package com.mars.hybrid.aos.core.extension

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/* =========================================================
 * 함수 확장
 * ========================================================= */
fun String.toTextBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaType())
