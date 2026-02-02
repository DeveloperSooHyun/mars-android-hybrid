package com.mars.hybrid.aos.core.common

import com.mars.hybrid.aos.core.manager.DeviceErrorType

/**
 * ======================================================
 * Title      : DeviceCheckResult
 * Developer  : Mars
 * Date       : 2026-01-30
 * Description:
 *  - 기기 상태 저장 변수 관리
 *
 * History:
 *  - [2026.01.30] DeviceCheckResult Initialization
 * ======================================================
 */
data class DeviceCheckResult(
    val isValid: Boolean,
    val type: DeviceErrorType? = null,
    val message: String = ""
)


