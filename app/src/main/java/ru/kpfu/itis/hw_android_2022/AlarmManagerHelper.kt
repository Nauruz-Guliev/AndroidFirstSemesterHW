package ru.kpfu.itis.hw_android_2022

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object AlarmManagerHelper {
    private var alarmManager: AlarmManager? = null
    private var alarmTime = 0L;

    fun setAlarm(delayTime: Int, activity: FragmentActivity?, intent: PendingIntent) {
        initAlarmManager(activity)
        alarmTime = SystemClock.elapsedRealtime() + delayTime * 1000
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            alarmTime,
            intent
        )
    }

    fun cancelAlarm(activity: FragmentActivity?, intent: PendingIntent) {
        initAlarmManager(activity)
        alarmManager?.cancel(intent)
        nullifyAlarmTime()
    }

    private fun initAlarmManager(activity: FragmentActivity?) {
        alarmManager = activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }

    fun getTimeRemaining() = alarmTime - SystemClock.elapsedRealtime()

    private fun nullifyAlarmTime() {
        alarmTime = 0
    }

}