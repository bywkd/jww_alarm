package com.jww.alarm.views.registerAlarmView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterAlarmVM : ViewModel() {

    class VMF() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterAlarmVM() as T
        }

    }
}