package ru.kpfu.itis.hw_android_2022.util

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

//иногда нагляднее так дебажить, чтобы быстрее было писать сделал расширение
fun <T> Fragment.showToast(message: T) =
    Toast.makeText(this.context, message.toString(), Toast.LENGTH_SHORT).show()

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}


/* с помощью дженериков получаю из бандла любой объект по ключу, который реализует интерфейс Parcelable
при этом используются разные методы для обратной совместимости
 */
fun <T : Parcelable> Fragment.getParcelable(
    arguments: Bundle?,
    ARGUMENT_KEY: String,
    className: Class<T>
): T? = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
    arguments?.getParcelable(ARGUMENT_KEY, className)
else arguments?.getParcelable(ARGUMENT_KEY) as T?


/*удобно хранить сообщение и действие в виде пары
так как не всегда необходимы обе кнопки, одна из пар нулабельна
 */
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

