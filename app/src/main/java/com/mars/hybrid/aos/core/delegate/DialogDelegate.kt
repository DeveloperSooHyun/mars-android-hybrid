package com.mars.hybrid.aos.core.delegate

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.mars.hybrid.aos.R
import androidx.core.graphics.drawable.toDrawable

/**
 * ======================================================
 * Title      : DialogDelegate
 * Developer  : Mars
 * Date       : 2026-02-02
 * Description:
 *  - 공통 Dialog
 *
 * History:
 *  - [2026.02.02] DialogDelegate Initialization
 * ======================================================
 */
class DialogDelegate(
    private val activity: Activity,
    private val uiCleanDelegate: UiCleanDelegate
) {

    private var dialog: AlertDialog? = null

    /* =========================================================
    * 커스텀 다이얼로그 
    * ========================================================= */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun showCustomDialog(
        title: String? = null,
        message: String,
        isConfirm: Boolean,
        onPositive: () -> Unit,
        onNegative: () -> Unit = {}
    ) {
        if (activity.isFinishing || activity.isDestroyed) return

        activity.runOnUiThread {

            // 기존 Dialog 제거
            dialog?.dismiss()
            dialog = null

            val layout = activity.layoutInflater
                .inflate(R.layout.activity_dialog, null)

            val builder = AlertDialog.Builder(activity)
                .setView(layout)
                .setCancelable(false)

            dialog = builder.create()

            dialog?.window?.setBackgroundDrawable(
                Color.TRANSPARENT.toDrawable()
            )
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

            // title
            title?.let {
                layout.findViewById<TextView>(R.id.dialog_title_text).text = it
            }

            // message
            layout.findViewById<TextView>(R.id.dialog_message_text).text = message

            val positiveBtn = layout.findViewById<AppCompatButton>(R.id.dialog_positive_btn)
            val negativeBtn = layout.findViewById<AppCompatButton>(R.id.dialog_negative_btn)

            positiveBtn.setOnClickListener {
                onPositive()
                dismiss()
            }

            if (isConfirm) {
                negativeBtn.visibility = View.VISIBLE
                negativeBtn.setOnClickListener {
                    onNegative()
                    dismiss()
                }
            } else {
                negativeBtn.visibility = View.GONE

                val lp = positiveBtn.layoutParams as LinearLayout.LayoutParams
                lp.weight = 2F
                lp.marginStart = 0
                positiveBtn.layoutParams = lp
                positiveBtn.background =
                    activity.getDrawable(R.drawable.btn_positive_selector)
            }

            dialog?.show()

            // UiCleanDelegate에 등록
            uiCleanDelegate.add {
                dismiss()
            }
        }
    }

    /* =========================================================
    * 다이얼로그 내림
    * ========================================================= */
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}

