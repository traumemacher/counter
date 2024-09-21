package com.example.counter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.counter.ServiceUtils.isServiceRunning

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            if (!isServiceRunning(context, StepCounterService::class.java)) {
                val startServiceIntent = Intent(context, StepCounterService::class.java)
                context.startForegroundService(startServiceIntent)
            }
        }
    }
}
