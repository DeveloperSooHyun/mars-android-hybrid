package com.mars.hybrid.aos.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.base.BaseActivity
import com.mars.hybrid.aos.core.common.AppInfo
import com.mars.hybrid.aos.core.util.DeviceUtil
import com.mars.hybrid.aos.data.datasource.RemoteDataSource
import com.mars.hybrid.aos.data.repository.AppRepository
import com.mars.hybrid.aos.databinding.ActivitySplashBinding
import com.mars.hybrid.aos.feature.network.ApiClient
import com.mars.hybrid.aos.ui.main.MainActivity
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay

/**
 * ======================================================
 * Title      : SplashActivity
 * Developer  : Mars
 * Date       : 2026-02-03
 * Description:
 *  - 초기 앱 진입 화면 (서버 버전 체크)
 *
 * History:
 *  - [2026.02.03] SplashActivity Initialization
 * ======================================================
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashMinDuration = 2000L
    private var startTime: Long = 0L
    private var isFlowStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTime = System.currentTimeMillis()
        startSplashFlow()
    }

    /* =========================================================
     * 스플래시 화면 시작
     * ========================================================= */
    private fun startSplashFlow() {
        if (isFlowStarted) return
        isFlowStarted = true

        checkAppVersion()
    }

    /* =========================================================
     * 서버 버전 체크
     * ========================================================= */
    private fun checkAppVersion() {
        lifecycleScope.launch {

            val repo = AppRepository(RemoteDataSource(ApiClient.apiService))

            // 서버 호출
            val result = repo.checkAppVersion(AppInfo().getAppVersion())

            // 최소 체류시간 보장
            waitForMinimumSplashTime()

            // 분기
            if (result.appUpdateYn == "Y") {
                showUpdateDialog()
            } else {
                moveToMain()
            }
        }
    }

    /* =========================================================
     * 최소 대기 시간
     * ========================================================= */
    private suspend fun waitForMinimumSplashTime() {
        val elapsed = System.currentTimeMillis() - startTime
        val remain = splashMinDuration - elapsed
        if (remain > 0) delay(remain)
    }

    /* =========================================================
     * 업데이트 다이얼로그
     * ========================================================= */
    private fun showUpdateDialog() {
        dialogDelegate.showCustomDialog(
            title = getString(R.string.title_dialog),
            message = getString(R.string.msg_required_update),
            isConfirm = false,
            onPositive = {
                DeviceUtil.openBrowser(this, AppInfo().getDownloadUrl())
            }
        )
    }

    /* =========================================================
     * Main 화면 이동
     * ========================================================= */
    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
