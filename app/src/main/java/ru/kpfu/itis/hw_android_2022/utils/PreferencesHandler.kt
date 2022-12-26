package ru.kpfu.itis.hw_android_2022.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import ru.kpfu.itis.hw_android_2022.R

class PreferencesHandler(private val ctx: Context) {

    private var preferences: SharedPreferences? = null
    private var defaultSp: SharedPreferences? = null

    init {
        preferences = ctx.getSharedPreferences(DEFAULT_SP_KEY, Context.MODE_PRIVATE)
        defaultSp = PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveUsername(username: String) {
        preferences?.edit()?.apply {
            this.putString(USERNAME_KEY, username)
                .apply()
        }
        defaultSp?.edit().apply {
            this?.putString(USERNAME_KEY, username)
        }?.apply()
        loadUserSettings(username)
    }

    fun getUsername(): String {
        val username = preferences?.getString(USERNAME_KEY, "") ?: ""
        return username
    }

    fun saveStringValue(key: String, input: String) {
        preferences?.edit()?.putString(key, input)?.apply()
    }

    fun removeUsername() {
        saveUserSettings(getUsername())
        preferences?.edit()?.putString(USERNAME_KEY, "")?.apply()
    }


    fun getStringValue(key: String): String? {
        return preferences?.getString(key, null)
    }

    // всё это не самый оптимальный подход и не масштабируемый
    // но мне было лень возиться с таблицами и с внешними ключами
    // так же для каждого пользователя свой sp
    private fun saveUserSettings(username: String) {
        val userPreferences = ctx.getSharedPreferences(username, Context.MODE_PRIVATE)

        val checkBoxFirst =
            defaultSp?.getBoolean(ctx.getString(R.string.check_box_first), true)?: true
        val checkBoxSecond =
            defaultSp?.getBoolean(ctx.getString(R.string.check_box_second), true)?: true
        val checkBoxThird =
            defaultSp?.getBoolean(ctx.getString(R.string.check_box_third), true)?: true

        userPreferences.edit().apply {
            putBoolean(ctx.getString(R.string.check_box_first), checkBoxFirst)
            putBoolean(ctx.getString(R.string.check_box_second), checkBoxSecond)
            putBoolean(ctx.getString(R.string.check_box_third), checkBoxThird)
        }.apply()
    }



    private fun loadUserSettings(username: String) {
        val userPreferences = ctx.getSharedPreferences(username, Context.MODE_PRIVATE)
        val checkBoxFirst =
            userPreferences.getBoolean(ctx.getString(R.string.check_box_first), true)
        val checkBoxSecond =
            userPreferences.getBoolean(ctx.getString(R.string.check_box_second), true)
        val checkBoxThird =
            userPreferences.getBoolean(ctx.getString(R.string.check_box_third), true)

        defaultSp?.edit()?.apply {
            putBoolean(ctx.getString(R.string.check_box_first), checkBoxFirst)
            putBoolean(ctx.getString(R.string.check_box_second), checkBoxSecond)
            putBoolean(ctx.getString(R.string.check_box_third), checkBoxThird)
        }?.apply()
    }

    companion object {
        private const val USERNAME_KEY = "USERNAME_KEY"
        private const val DEFAULT_SP_KEY = "DEFAULT_SP_KEY"
    }
}