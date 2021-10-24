package com.jww.alarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import com.jww.alarm.services.AlarmBackgroundService
import com.jww.alarm.services.LockScreenService
import com.jww.alarm.views.alarmPlayView.AlarmLockScreenView


class AlarmReceiver : BroadcastReceiver() {

    companion object {
        val NOTI_ID_ALARM_RECEVER = 9998
    }

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

            Log.d("Won", "${intent?.action}")
            sCpuWakeLock?.acquire()
//            startLockScreenService(it)
//            startAlarmBackgroundService(it)
//            startLockScreenActivity(context, intent?.action)

            sCpuWakeLock?.release()
            sCpuWakeLock ?: let {
                sCpuWakeLock = null
            }
            Log.d("Won", "2================================")
        }

        Log.d("Won", "1================================")

        when (intent?.action) {
            "com.test" -> {
                Log.d("Won", "ACTION_BOOT_COMPLETED")
                startLockScreenActivity(context!!, "com.test")
            }
        }
    }

    private fun startAlarmBackgroundService(context: Context) {
        Log.d("Won", "startAlarmBackgroundService")

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
        Log.d("Won", "context = $context")
    }

    private fun startLockScreenActivity(context: Context, action: String?) {
        val intent = Intent(context, AlarmLockScreenView::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        Log.d("Won","startActivity ${action}")
        context.startActivity(intent)
    }

    private fun startLockScreenService(context: Context) {
        val serviceIntent = Intent(context, LockScreenService::class.java)
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