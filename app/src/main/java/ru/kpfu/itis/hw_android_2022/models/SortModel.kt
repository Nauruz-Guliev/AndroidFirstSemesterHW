package ru.kpfu.itis.hw_android_2022.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class SortModel : Parcelable {
    NAME_ASC,
    NAME_DESC,
    ID_DESC,
    ID_ASC,
}