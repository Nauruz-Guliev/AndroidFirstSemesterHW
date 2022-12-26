package ru.kpfu.itis.hw_android_2022.fragments.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.utils.PreferencesHandler
import ru.kpfu.itis.hw_android_2022.utils.showToast

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)
    }

}