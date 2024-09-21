package com.example.counter

import android.app.ActivityManager
import android.content.Context

object ServiceUtils {
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.getRunningServices(Integer.MAX_VALUE).any { it.service.className == serviceClass.name }
    }
}