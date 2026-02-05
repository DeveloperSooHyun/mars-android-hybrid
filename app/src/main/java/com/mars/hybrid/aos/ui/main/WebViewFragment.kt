package com.mars.hybrid.aos.ui.main


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings

import androidx.activity.addCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

import com.mars.hybrid.aos.core.common.AppInfo
import com.mars.hybrid.aos.core.util.CommonUtil
import com.mars.hybrid.aos.core.util.DeviceUtil
import com.mars.hybrid.aos.core.util.PreferenceUtil
import com.mars.hybrid.aos.databinding.FragmentWebviewBinding
import com.mars.hybrid.aos.feature.auth.BiometricAuthManager
import com.mars.hybrid.aos.ui.camera.CameraActivity
import com.mars.hybrid.aos.ui.main.bridge.BridgeHandler
import com.mars.hybrid.aos.ui.main.bridge.MainBridge
import com.mars.hybrid.aos.ui.main.manager.WebChromeManager
import com.mars.hybrid.aos.ui.main.manager.WebViewClientManager

import org.json.JSONObject


/**
 * ======================================================
 * Title      : WebViewFragment
 * Developer  : Mars
 * Date       : 2026-01-28
 * Description:
 *  - WebView 세팅, JS Bridge 연결, JS 함수 집합
 *
 * History:
 *  - [2026.01.28] WebViewFragment Initialization
 * ======================================================
 */
class WebViewFragment : Fragment(), BridgeHandler {

    private lateinit var binding: FragmentWebviewBinding
    private lateinit var bridge: MainBridge
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>

    /* =========================================================
     * LifeCycle
     * ========================================================= */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLauncher()
        initWebView()
        initBackPressed()
        loadInitialPage()
    }

    override fun onDestroyView() {
        binding.webView.apply {
            loadUrl("about:blank")
        }
        super.onDestroyView()
    }

    /* =========================================================
     * launcher 초기화
     * ========================================================= */
    private fun initLauncher() {
        intentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                handleActivityResult(it)
            }
    }

    /* =========================================================
     * webview 초기화
     * ========================================================= */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        bridge = MainBridge(this)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.setSupportZoom(false)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.textZoom = 100
            settings.setGeolocationEnabled(true)
            settings.userAgentString += " MARS_APP"

            webViewClient = WebViewClientManager { secure ->
                val window = requireActivity().window
                if (secure) window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                else window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }

            webChromeClient = WebChromeManager(this@WebViewFragment)
            addJavascriptInterface(bridge, "mars")
        }

        binding.swipeLayout.setOnRefreshListener {
            sendToJs("RELOAD", JSONObject())
            binding.swipeLayout.isRefreshing = false
        }
    }

    /* =========================================================
     * 웹 뷰 URL 연결
     * ========================================================= */
    private fun loadInitialPage() {
        val url = "${AppInfo().getServerUrl()}/login"
        binding.webView.loadUrl(url)
    }

    /* =========================================================
     * 뒤로가기 호출 시
     * ========================================================= */
    private fun initBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            sendToJs("BACK", JSONObject())
        }
    }

    /* =========================================================
     * Bridge 통신
     * ========================================================= */
    override fun onBridgeMessage(mode: String, param: JSONObject) {
        when (mode) {
            "DEVICE" -> sendDeviceInfo()
            "TOKEN" -> handleToken(param)
            "CAMERA" -> openCamera(param)
            "DIAL" -> dial(param)
            "TEL" -> handleTel(param)
            "COOKIE" -> handlePreference(param)
            "DEL_COOKIE" -> deletePreference(param)
            "FINISH" -> handleFinish()
            "FIDO" -> handleBiometric(mode,param)
            "PASS" -> handlePass(mode,param)
        }
    }

    override fun onBridgeCallback(mode: String, param: JSONObject) {
        sendToJs(mode, param)
    }

    /* =========================================================
     * JS callback
     * ========================================================= */
    private fun sendToJs(mode: String, param: JSONObject) {
        val rtn = CommonUtil.jsCallback(mode, param)

        binding.webView.post {
            binding.webView.evaluateJavascript(
                "postMessageCallback('$rtn');",
                null
            )
        }
    }


    /* =========================================================
     * 카메라
     * ========================================================= */
    private fun openCamera(param: JSONObject) {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        param.keys().forEach { key ->
            intent.putExtra(key, param.optString(key))
        }
        intentLauncher.launch(intent)
    }

    /* =========================================================
     * 전화
     * ========================================================= */
    private fun dial(param: JSONObject) {
        val telNo = param.optString("tel")
        startActivity(Intent(Intent.ACTION_DIAL, "tel:$telNo".toUri()))
    }

    private fun handleTel(param: JSONObject) {
        val telNo = param.optString("tel")
        if (telNo != "") {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = "tel:$telNo".toUri()
            requireContext().startActivity(intent)
        }
    }

    /* =========================================================
     * 디바이스 정보
     * ========================================================= */
    private fun sendDeviceInfo() {
        val json = JSONObject().apply {
            put("model", Build.MODEL)
            put("osVer", Build.VERSION.RELEASE)
            put("appVer", AppInfo().getAppVersion())
        }
        sendToJs("DEVICE", json)
    }

    /* =========================================================
     * 토큰 조회
     * ========================================================= */
    private fun handleToken(param: JSONObject) {
        PreferenceUtil.putString("token", param.optString("token"))
    }

    /* =========================================================
     * 쿠키 정보 관리
     * ========================================================= */
    private fun handlePreference(param: JSONObject) {
        val key = param.optString("key")
        val value = param.optString("value")

        if (value.isEmpty()) {
            // 조회 리턴
            val rtn = JSONObject().put("key", key).put("value", PreferenceUtil.getString(key, ""))
            sendToJs("COOKIE", rtn)
        } else {
            // 저장
            PreferenceUtil.putString(key, value)

            // POPUP_YN 특수 처리
            if (key == "POPUP_YN") {
                if (value == "N") {
                    binding.swipeLayout.post {
                        binding.swipeLayout.isEnabled = true
                        binding.swipeLayout.setOnRefreshListener {
                            sendToJs("RELOAD", JSONObject())
                            binding.swipeLayout.isRefreshing = false
                        }
                    }
                } else {
                    binding.swipeLayout.post {
                        binding.swipeLayout.isEnabled = false
                        binding.swipeLayout.setOnRefreshListener(null)
                    }
                }
            }
        }
    }


    private fun deletePreference(param: JSONObject) {
        val key = param.optString("key")
        PreferenceUtil.remove(key)
    }

    /* =========================================================
     * 생체인증
     * ========================================================= */
    private fun handleBiometric(mode:String, param: JSONObject) {
        requireActivity().runOnUiThread {
            val biometric = BiometricAuthManager(requireActivity()) { result ->
                when (result.optString("result")) {

                    "SUCCESS" -> {
                        param.put("resultCd", "SUCCESS")
                        param.put("resultMsg", "SUCCESS: 성공")
                        sendToJs(mode, param)
                    }

                    "BLOCKED" -> {
                        param.put("resultCd", "FAIL")
                        param.put("resultMsg", "BLOCKED: 인증 3회 실패")
                        sendToJs(mode, param)
                    }

                    "USER_CANCELED" -> {
                        param.put("resultCd", "FAIL")
                        param.put("resultMsg", "USER_CANCELED: 인증 임시 사용 불가")
                        sendToJs(mode, param)
                    }

                    "NOT_AVAILABLE" -> {
                        param.put("resultCd", "FAIL")
                        param.put("resultMsg", "NOT_AVAILABLE: 기기 지원 불가")
                        sendToJs(mode, param)
                    }
                }
            }

            biometric.authenticate()
        }
    }

    /* =========================================================
     * PASS 인증
     * ========================================================= */
    private fun handlePass(mode: String, param: JSONObject) {
        param.put("resultCd", "E0000")
        param.put("resultMsg", "E0000: 인증 성공")
        // PASS 앱 없으면 E3001 / 패스 서비스 미가입자 등 PASS 가이드 따름
        // PASS 앱 있으나 E9999 / 앱 미설치 >> 리턴보내고 나는 확인 알림 이후 확인 누르면 설치 페이지로 이동 시킴.
        sendToJs(mode, param)
    }

    /* =========================================================
     * 앱 종료
     * ========================================================= */
    private fun handleFinish() { DeviceUtil.finishApp(requireActivity()) }

    /* =========================================================
     * 화면 결과 처리
     * ========================================================= */
    private fun handleActivityResult(result: ActivityResult) {
        //bridge.handleActivityResult(result)
    }
}
