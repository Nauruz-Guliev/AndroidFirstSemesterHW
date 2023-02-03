package ru.kpfu.itis.hw_android_2022.models

sealed class UIModel(val id: Int) {

    class HeaderModel(val headerText:String, val ID: Int) : UIModel(ID)

    class FirstTypeModel(val firstItem: FirstItem, val ID: Int): UIModel(ID)

    class SecondTypeModel(val secondItem: SecondItem, val ID: Int) : UIModel(ID)

    class ThirdTypeModel(val thirdItem: ThirdItem, val ID: Int) : UIModel(ID)
}