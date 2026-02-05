package com.mars.hybrid.aos.feature.auth

import android.app.Activity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import org.json.JSONObject

/**
 * ======================================================
 * Title      : BiometricAuthManager
 * Developer  : Mars
 * Date       : 2026-02-05
 * Description:
 *  - 생체 인증(Fingerprint / Face) 관리 클래스
 *  - 3회 실패 시 앱 레벨 차단
 *
 * History:
 *  - [2026.02.05] BiometricAuthManager Initialization
 * ======================================================
 */
class BiometricAuthManager(
    activity: Activity,
    private val callback: (JSONObject) -> Unit
) {

    private val fragmentActivity = activity as FragmentActivity
    private val executor = ContextCompat.getMainExecutor(activity)
    private val biometricManager = BiometricManager.from(activity)

    private var failCount = 0
    private val maxCount = 3

    private lateinit var biometricPrompt: BiometricPrompt

    fun isAvailable(): Boolean {
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticate() {
        val title = "생체 인증"
        val subtitle = "본인 확인을 진행합니다"

        if (!isAvailable()) {
            callback(JSONObject().put("result", "NOT_AVAILABLE"))
            return
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText("취소")
            .build()

        biometricPrompt = BiometricPrompt(
            fragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    callback(JSONObject().put("result", "SUCCESS"))
                }

                override fun onAuthenticationFailed() {
                    failCount++

                    if (failCount >= maxCount) {
                        biometricPrompt.cancelAuthentication()

                        callback(
                            JSONObject().apply {
                                put("result", "BLOCKED")
                                put("failCount", failCount)
                            }
                        )
                    }
                    // ❗ 여기서 callback(Fail) 안 보냄
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    val result = JSONObject()

                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            result.put("result", "USER_CANCELED")
                        }

                        BiometricPrompt.ERROR_LOCKOUT,
                        BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                            result.put("result", "BLOCKED")
                        }

                        else -> {
                            result.put("result", "ERROR")
                            result.put("message", errString.toString())
                        }
                    }

                    callback(result)
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }
}
