package com.jww.alarm.views.settingView

import android.os.Bundle
import com.jww.alarm.bases.BaseFragment

class SettingFragment : BaseFragment() {
    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment().apply {
                val arguments = Bundle().apply {
                    putString("", "")
                }
                this.arguments = arguments
            }
        }
    }
}