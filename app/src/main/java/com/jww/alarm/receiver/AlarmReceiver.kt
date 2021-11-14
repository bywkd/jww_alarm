package com.jww.alarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import com.jww.alarm.views.alarmPlayView.AlarmLockScreenView


class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ALARM_RECEIVER_CODE = 9998
    }

    var sCpuWakeLock: PowerManager.WakeLock? = null

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            startPowerSystem(it)
        }
    }


    private fun startLockScreenActivity(context: Context) {
        val intent = Intent(context, AlarmLockScreenView::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }


    @SuppressLint("InvalidWakeLockTag")
    private fun startPowerSystem(context: Context) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        sCpuWakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "alarmReceiver"
        )

        sCpuWakeLock?.acquire()
        startLockScreenActivity(context)
        sCpuWakeLock?.release()
        sCpuWakeLock ?: let {
            sCpuWakeLock = null
        }
    }
}