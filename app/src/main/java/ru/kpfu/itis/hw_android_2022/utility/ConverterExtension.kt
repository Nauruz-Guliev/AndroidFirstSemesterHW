package ru.kpfu.itis.hw_android_2022.models

import android.content.Context
import android.util.TypedValue


fun Int.convertPxToDp(context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()



fun Int.convertDpToPx(context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()