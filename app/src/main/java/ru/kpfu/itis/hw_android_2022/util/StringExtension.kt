package ru.kpfu.itis.hw_android_2022.util

fun String.checkUrl() = if (!this.startsWith("http://") && !this.startsWith("https://"))
        "https://" + this else this