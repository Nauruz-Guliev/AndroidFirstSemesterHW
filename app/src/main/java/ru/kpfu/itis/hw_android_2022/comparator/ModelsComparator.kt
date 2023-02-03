package ru.kpfu.itis.hw_android_2022.comparator

import ru.kpfu.itis.hw_android_2022.models.UIModel

object ModelsComparator {
    fun comparator() = Comparator<UIModel> { a, b ->
        when {
            a is UIModel.FirstTypeModel && b is UIModel.SecondTypeModel -> -1
            a is UIModel.FirstTypeModel && b is UIModel.ThirdTypeModel -> -1
            a is UIModel.FirstTypeModel && b is UIModel.HeaderModel -> -1

            a is UIModel.SecondTypeModel && b is UIModel.FirstTypeModel -> 1
            a is UIModel.SecondTypeModel && b is UIModel.ThirdTypeModel -> -1
            a is UIModel.SecondTypeModel && b is UIModel.HeaderModel -> -1

            a is UIModel.HeaderModel && b is UIModel.FirstTypeModel -> 1
            a is UIModel.HeaderModel && b is UIModel.SecondTypeModel -> 1
            a is UIModel.HeaderModel && b is UIModel.ThirdTypeModel -> -1

            a is UIModel.ThirdTypeModel && b is UIModel.FirstTypeModel -> 1
            a is UIModel.ThirdTypeModel && b is UIModel.SecondTypeModel -> 1
            a is UIModel.ThirdTypeModel && b is UIModel.HeaderModel -> 1
            else -> 0
        }
    }
}