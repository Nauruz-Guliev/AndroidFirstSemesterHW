package ru.kpfu.itis.hw_android_2022

import java.util.*

object AirModeInterfaceImpl : AirModeInterface {

    private val observers = mutableListOf<ru.kpfu.itis.hw_android_2022.Observer>()
    var isOn = false
    override fun registerObserver(observer: ru.kpfu.itis.hw_android_2022.Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: ru.kpfu.itis.hw_android_2022.Observer) {
        observers.remove(observer)
    }

    override fun notifyObserver() {
        observers.forEach {
            it.update(isOn)
        }
    }
}