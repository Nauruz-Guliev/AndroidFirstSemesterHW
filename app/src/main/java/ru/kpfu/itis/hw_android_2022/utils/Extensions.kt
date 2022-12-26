package ru.kpfu.itis.hw_android_2022.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.math.BigInteger
import java.security.MessageDigest


fun<T> Context.showToast(message: T) = Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun String.toMd5() : String {
    val crypt = MessageDigest.getInstance("MD5");
    crypt.update(this.toByteArray());
    return BigInteger(1, crypt.digest()).toString(16)
}

fun Fragment.validateInput(
    userName: String?,
    password: String?,
    passwordConfirm: String? = null
) : Boolean {
    if(passwordConfirm == null) {
        if (userName.isNullOrBlank() || password.isNullOrBlank()) {
            return false
        }
    } else {
        if (userName.isNullOrBlank() || password.isNullOrBlank() || (password != passwordConfirm)) {
            return false
        }
    }
    return true
}

fun View.showSnackbar(
    msg: String,
    actionMessage: CharSequence?,
    action: () -> Unit
) {
    val snackbar = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action()
        }.show()
    } else {
        snackbar.show()
    }
}