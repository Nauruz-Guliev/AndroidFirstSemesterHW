package ru.kpfu.itis.hw_android_2022

import java.util.Observer

interface AirModeInterface {
    fun registerObserver(observer: ru.kpfu.itis.hw_android_2022.Observer)
    fun removeObserver(observer: ru.kpfu.itis.hw_android_2022.Observer)
    fun notifyObserver()
}