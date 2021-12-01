package com.jww.alarm.views.registerAlarmView

import android.content.Context
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.db.AlarmDataEntity
import com.jww.alarm.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class RegisterAlarmVM : ViewModel() {
    val isRegister = MutableLiveData(true)
    val registerUid = MutableLiveData(0L)
    val isSound = MutableLiveData(true)
    val isVibration = MutableLiveData(true)
    var hour: Int = 0
    var min: Int = 0
    var year: Int = 0
    var month: Int = 0
    var dayOfMonth: Int = 0

    val calendar by lazy { Calendar.getInstance() }

    val timerListener = TimePicker.OnTimeChangedListener { timePicker, hour, min ->
        this.hour = hour
        this.min = min
        calendar.set(this.year, this.month, this.dayOfMonth, this.hour, this.min)
    }

    val calendarListener =
        CalendarView.OnDateChangeListener { calendarView, year, month, dayOfMonth ->
            this.year = year
            this.month = month
            this.dayOfMonth = dayOfMonth
            calendar.set(this.year, this.month, this.dayOfMonth, this.hour, this.min)
        }

    fun uiInitSound(isSound: Boolean): RegisterAlarmVM {
        this.isSound.value = isSound
        return this
    }

    fun uiInitVibration(isVibration: Boolean): RegisterAlarmVM {
        this.isVibration.value = isVibration
        return this
    }

    fun uiInitTime(timePicker: TimePicker, hour: Int, min: Int): RegisterAlarmVM {
        timePicker.apply {
            this.hour = hour
            this.minute = min
        }
        this.hour = hour
        this.min = min
        return this
    }

    fun uiInitCalendar(
        calendarView: CalendarView,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ): RegisterAlarmVM {
        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth

        calendar.set(this.year, this.month, this.dayOfMonth)
        calendarView.date = calendar.timeInMillis
        return this
    }


    fun setSound() {
        this.isSound.value = !this.isSound.value!!
    }

    fun setVibration() {
        this.isVibration.value = !this.isVibration.value!!
    }


    fun completeRegister(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val data = AlarmDataEntity(
                0,
                hour,
                min,
                year,
                month,
                dayOfMonth,
                isSound.value!!,
                isVibration.value!!,
                "",
                true
            )
            val uId = AppDatabase.getInstance(context)?.getAlarmDao()?.insert(data)
            uId?.let {
                launch(Dispatchers.Main) {
                    registerUid.value = it
                }
            }

        }
    }

    class VMF() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterAlarmVM() as T
        }
    }
}