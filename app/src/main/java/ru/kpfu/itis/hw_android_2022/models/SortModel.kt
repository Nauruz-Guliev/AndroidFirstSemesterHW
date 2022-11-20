package ru.kpfu.itis.hw_android_2022.models

import android.os.Parcelable
import androidx.annotation.StringRes
import ru.kpfu.itis.hw_android_2022.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class SortModel(@StringRes val textResId: Int) : Parcelable {
    NAME_ASC(R.string.name_asc),
    NAME_DESC(R.string.name_desc),
    ID_DESC(R.string.id_desc),
    ID_ASC(R.string.id_asc),
}