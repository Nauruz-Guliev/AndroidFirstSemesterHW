package ru.kpfu.itis.hw_android_2022.models


data class FirstItem(
    val primaryName: String,
    val duration: String,
    val secondaryName: String,
    var imageUrl: String
)

data class SecondItem(
    val imageDrawableId: Int,
    val primaryName: String
)

data class ThirdItem(
    val imageUrl: String,
    val duration: String,
    val primaryName: String,
    val secondaryName: String
)

