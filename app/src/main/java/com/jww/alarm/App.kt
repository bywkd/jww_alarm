package com.jww.alarm

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class App : Application() {

    private lateinit var vib: Vibrator

    override fun onCreate() {
        super.onCreate()
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }

    fun startVibrator(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder().build()
            vib.vibrate(
                VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE),
                audioAttributes)
        }else{
            vib.vibrate(200)
        }
    }
}