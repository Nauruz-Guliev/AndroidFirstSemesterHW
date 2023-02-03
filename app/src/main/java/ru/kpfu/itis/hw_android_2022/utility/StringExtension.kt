package ru.kpfu.itis.hw_android_2022.models

import android.content.res.Resources
import android.text.Html
import android.text.Spanned

fun String.colorizeText(
    firstColor: Int,
    secondColor: Int,
    secondText: String,
    resources: Resources
): Spanned =
     Html.fromHtml(
        "<font color=${
            resources.getColor(
                firstColor,
                null
            )
        }>${this}</font> <font color=${resources.getColor(secondColor, null)}>${secondText}</font>",
        0
    )




