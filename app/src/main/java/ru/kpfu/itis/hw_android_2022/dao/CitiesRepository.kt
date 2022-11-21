package ru.kpfu.itis.hw_android_2022.dao

import ru.kpfu.itis.hw_android_2022.models.City
import ru.kpfu.itis.hw_android_2022.models.SortModel

object CitiesRepository {

    private val items = mutableListOf(
        City(1, "Edinburgh"),
        City(2, "Chicago"),
        City(3, "Medellin"),
        City(4, "Glasgow"),
        City(5, "Amsterdam"),
        City(6, "Prague"),
        City(7, "Marrakesh"),
        City(8, "Berlin"),
        City(9, "Montreal"),
        City(10, "Copenhagen"),
        City(11, "Cape Town"),
        City(12, "Madrid"),
        City(13, "Manchester"),
        City(14, "Mumbai"),
        City(15, "Melbourne")
    )
    private var selectedSort: SortModel? = SortModel.ID_ASC

    val cities: List<City>
        get() = when (selectedSort) {
            SortModel.ID_ASC -> items.sortedBy { it.id }
            SortModel.ID_DESC -> items.sortedByDescending { it.id }
            SortModel.NAME_ASC -> items.sortedBy { it.name }
            SortModel.NAME_DESC -> items.sortedByDescending { it.name }
            else -> items.sortedBy { it.id }
        }

    fun setSelectedSort(sort: SortModel?) {
        selectedSort = sort?: selectedSort
    }

    fun getSelectedSort() = selectedSort

}