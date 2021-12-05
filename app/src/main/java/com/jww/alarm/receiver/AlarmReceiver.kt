package com.jww.alarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import com.jww.alarm.db.AppDatabase
import com.jww.alarm.views.alarmLockScreen.AlarmLockScreenActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ALARM_RECEIVER_CODE = 9998
    }

    var sCpuWakeLock: PowerManager.WakeLock? = null

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context?, intent: Intent?) {
        val uid = intent?.getLongExtra("uid", 0L) ?: 0L
        Log.d("Won", "uid recevie = $uid")
        if (uid > 0L) {
            context?.let {
                checkAlarm(context, uid)
            }
        }
    }

    private fun startLockScreenActivity(context: Context, sound: Boolean, vibration: Boolean) {
        val intent = Intent(context, AlarmLockScreenActivity::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("sound", sound)
            putExtra("vibration", vibration)
        }
        context.startActivity(intent)
    }

    @SuppressLint("InvalidWakeLockTag")
    private fun startPowerSystem(context: Context, sound: Boolean, vibration: Boolean) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        sCpuWakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "alarmReceiver"
        )

        sCpuWakeLock?.acquire()
        startLockScreenActivity(context, sound, vibration)
        sCpuWakeLock?.release()
        sCpuWakeLock ?: let {
            sCpuWakeLock = null
        }
    }

    private fun checkAlarm(context: Context, uid: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val alarm = AppDatabase.getInstance(context)?.getAlarmDao()?.isAlarms(uid)
            val isActive = alarm?.isActive ?: false
            val sound = alarm?.sound ?: false
            val vibration = alarm?.vibration ?: false
            if (isActive) {
                startPowerSystem(context, sound, vibration)
            }
        }
    }
}