package com.jww.alarm.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AlertDialog

class DialogUtil(context: Context) {
    private val dlg: AlertDialog.Builder = AlertDialog.Builder(context)
    private var dialog: AlertDialog? = null

    init {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        dlg.apply {
            setCancelable(false)
        }
    }

    fun setTitle(title: String): DialogUtil {
        dlg.setTitle(title)
        return this
    }

    fun setMessage(message: String): DialogUtil {
        dlg.setMessage(message)
        return this
    }

    fun addBtnOK(funListenerOK: (() -> Unit)?): DialogUtil {
        dlg.setPositiveButton(
            "Ok"
        ) { dialogInterface, _ ->
            funListenerOK?.let {
                it()
            }
            dialogInterface.dismiss()
        }
        return this
    }

    fun addBtnCancel(funListenerCancel: (() -> Unit)?): DialogUtil {
        dlg.setNegativeButton(
            "Cancel"
        ) { dialogInterface, _ ->
            funListenerCancel?.let {
                it()
            }
            dialogInterface.dismiss()
        }
        return this
    }

    fun show() {
        dialog = dlg.show()
    }
}