package com.mars.hybrid.aos.ui.permission

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.base.BaseActivity
import com.mars.hybrid.aos.core.delegate.PermissionDelegate
import com.mars.hybrid.aos.databinding.ActivityPermissionBinding
import androidx.core.view.isEmpty
import com.mars.hybrid.aos.core.util.DeviceUtil

/**
 * ======================================================
 * Title      : PermissionActivity
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - 권한 화면
 *  - delegate 연결점
 *
 * History:
 *  - [2026.02.02] PermissionActivity Initialization
 * ======================================================
 */
class PermissionActivity : BaseActivity() {

    private lateinit var binding: ActivityPermissionBinding
    private lateinit var permissionDelegate: PermissionDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionDelegate = PermissionDelegate(this)

        if (binding.permissionContainer.isEmpty()) {
            setupItems()
        }

        binding.permissionCheckedBtn.setOnClickListener {
            checkPermission()
        }
    }

    /* =========================================================
     * 권한 리스트 생성
     * ========================================================= */
    private fun setupItems() {
        val container = binding.permissionContainer

        val items = listOf(
            PermissionItem(R.drawable.ic_bluetooth, getString(R.string.title_bluetooth), getString(R.string.msg_required_bluetooth)),
            PermissionItem(R.drawable.ic_camera, getString(R.string.title_camera), getString(R.string.msg_required_camera)),
            PermissionItem(R.drawable.ic_gallery, getString(R.string.title_gallery), getString(R.string.msg_required_gallery)),
            PermissionItem(R.drawable.ic_location, getString(R.string.title_location), getString(R.string.msg_required_location)),
            PermissionItem(R.drawable.ic_call, getString(R.string.title_call), getString(R.string.msg_required_call)),
            PermissionItem(R.drawable.ic_notifications, getString(R.string.title_notifications), getString(R.string.msg_required_notifications))
        )

        items.forEach {
            val view = layoutInflater.inflate(R.layout.item_permission, container, false)
            view.findViewById<ImageView>(R.id.ivIcon).setImageResource(it.icon)
            view.findViewById<TextView>(R.id.tvTitle).text = it.title
            view.findViewById<TextView>(R.id.tvDesc).text = it.desc
            container.addView(view)
        }
    }

    /* =========================================================
     * 권한 체크
     * ========================================================= */
    private fun checkPermission() {
        permissionDelegate.requestIfNeeded {
            versionCheck()
        }
    }

    /* =========================================================
     * 권한 결과 리턴
     * ========================================================= */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionDelegate.onRequestPermissionsResult(
            permissions,
            grantResults,
            onDenied = {
                dialogDelegate.showCustomDialog(
                    getString(R.string.title_dialog),
                    getString(R.string.msg_required_permission),
                    false,{
                        DeviceUtil.openSetting(this)
                    }
                ) {}
            },
            onAllGranted = {
                versionCheck()
            }
        )
    }

    /* =========================================================
     * 권한 성공 시 버전 체크
     * ========================================================= */
    private fun versionCheck() {
        // 👉 여기서 Api 호출 (Repository로)
    }
}

/* =========================================================
 * 권한 리스트 내용 구조
 * ========================================================= */
data class PermissionItem(
    val icon: Int,
    val title: String,
    val desc: String
)