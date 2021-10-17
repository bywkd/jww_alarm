package com.jww.alarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import com.jww.alarm.services.AlarmBackgroundService
import com.jww.alarm.views.alarmPlayView.AlarmPlayView


class AlarmReceiver : BroadcastReceiver() {
    var sCpuWakeLock: PowerManager.WakeLock? = null

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val pm = it.getSystemService(Context.POWER_SERVICE) as PowerManager
            sCpuWakeLock =
                pm.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
                    "test"
                )

            sCpuWakeLock?.acquire(10 * 1000L /*10 minutes*/)

            startAlarmBackgroundService(it)
            sCpuWakeLock?.release()
            sCpuWakeLock = null

            Log.d("Won", "2================================")
        }

        Log.d("Won", "1================================")
    }

    private fun startAlarmBackgroundService(context: Context) {
        Log.d("Won", "startAlarmBackgroundService")
        val intent = Intent(context, AlarmPlayView::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        val serviceIntent = Intent(context, AlarmBackgroundService::class.java)
        serviceIntent.apply {
            action = Intent.ACTION_SCREEN_ON
            action = Intent.ACTION_SCREEN_OFF

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }

    }
}