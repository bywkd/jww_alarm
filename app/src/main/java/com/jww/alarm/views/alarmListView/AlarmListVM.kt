package com.jww.alarm.views.alarmListView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmListVM : ViewModel() {

    class VMF() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AlarmListVM() as T
        }

    }
}