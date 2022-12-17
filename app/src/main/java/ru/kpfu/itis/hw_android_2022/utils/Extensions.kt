package ru.kpfu.itis.hw_android_2022.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigInteger
import java.security.MessageDigest


fun<T> Fragment.showToast(message: T) = Toast.makeText(this.context, message.toString(), Toast.LENGTH_SHORT).show()

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