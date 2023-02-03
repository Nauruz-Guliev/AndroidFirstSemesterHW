package ru.kpfu.itis.hw_android_2022

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(context: Context, message:String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

