package ru.kpfu.itis.hw_android_2022

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class FlyModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getBooleanExtra("state", false)?.let {
            AirModeInterfaceImpl.isOn = it
            AirModeInterfaceImpl.notifyObserver()
        }
    }
}