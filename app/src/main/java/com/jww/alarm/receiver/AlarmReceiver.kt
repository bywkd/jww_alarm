package com.jww.alarm.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.jww.alarm.services.AlarmBackgroundService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            startAlarmBackgroundService(it)
        }

        Log.d("Won", "================================")
    }

    private fun startAlarmBackgroundService(context: Context) {
        val serviceLauncher = Intent(context, AlarmBackgroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceLauncher);
        } else {
            context.startService(serviceLauncher);
        }
    }


    public fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            Log.d("Won", "serviceRunning =  ${service.service.className}")
            if (serviceClass.name.equals(service.service.className)) {
                Log.d("Won", "serviceRunning = " + true)

            }
        }
        return false
    }
}