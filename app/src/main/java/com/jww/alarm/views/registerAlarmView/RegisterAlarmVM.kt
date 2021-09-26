package com.jww.alarm.views.registerAlarmView

import android.util.Log
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DateFormat
import java.util.*

class RegisterAlarmVM : ViewModel() {
    val isRegister = MutableLiveData(true)
    val isSound = MutableLiveData(true)
    val isVibration = MutableLiveData(true)
    var hour: Int = 0
    var min: Int = 0
    var year: Int = 0
    var month: Int = 0
    var dayOfMonth: Int = 0

    private val calendar by lazy { Calendar.getInstance() }

    val timerListener = TimePicker.OnTimeChangedListener { timePicker, hour, min ->
        this.hour = hour
        this.min = min
        Log.d("Won", "$hour = hour    $min  = min")
    }

    val calendarListener =
        CalendarView.OnDateChangeListener { calendarView, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val formattedDate = dateFormatter.format(calendar.time)
            Log.d("Won", formattedDate)
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

        calendar.set(this.year, this.month - 1, this.dayOfMonth)
        calendarView.date = calendar.timeInMillis
        return this
    }


    fun setSound() {
        this.isSound.value = !this.isSound.value!!
    }

    fun setVibration() {
        this.isVibration.value = !this.isVibration.value!!
    }


    fun completeRegister() {

    }

    class VMF() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterAlarmVM() as T
        }

    }
}