package com.mars.hybrid.aos.ui.main

import android.os.Bundle
import com.mars.hybrid.aos.R
import com.mars.hybrid.aos.core.base.BaseActivity

/**
 * ======================================================
 * Title      : MainActivity
 * Developer  : Mars
 * Date       : 2026-01-28
 * Description:
 *  - webview fragment 틀
 *
 * History:
 *  - [2026.01.28] MainActivity Initialization
 * ======================================================
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WebViewFragment())
                .commit()
        }
    }
}