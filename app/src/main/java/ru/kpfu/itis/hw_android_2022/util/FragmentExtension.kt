package ru.kpfu.itis.hw_android_2022.util

import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}


// с помощью дженериков ограничил только до тех, кто реализует Parcelable,
// но при этом не знаю, как передать название класса, как параметр для каста??
fun <T : Parcelable> Fragment.getParcelable(
    arguments: Bundle?,
    ARGUMENT_KEY: String,
    className: Class<T>
): Parcelable? {
    return if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(ARGUMENT_KEY, className)
    } else {
        //интересно, а как здесь сразу можно скастить к нужному классу? пример:
        // arguments?.getParcelable(ARGUMENT_KEY) as className
        arguments?.getParcelable(ARGUMENT_KEY)
    }
}

fun Fragment.showAlert(
    title: String,
    message: String,
    positiveAction: Pair<String, (() -> Unit)?>,
    negativeAction: Pair<String, (() -> Unit)?>?
) {
    AlertDialog.Builder(this.requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveAction.first) { _, _ -> positiveAction.second?.invoke() }
        .setNegativeButton(negativeAction?.first) { _, _ -> negativeAction?.second?.invoke() }
        .create()
        .show()
}

