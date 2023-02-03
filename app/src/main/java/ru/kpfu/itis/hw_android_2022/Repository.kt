package ru.kpfu.itis.hw_android_2022

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import ru.kpfu.itis.hw_android_2022.comparator.ModelsComparator
import ru.kpfu.itis.hw_android_2022.data.RandomItemsGenerator
import ru.kpfu.itis.hw_android_2022.models.SecondItem
import ru.kpfu.itis.hw_android_2022.models.UIModel

object Repository {
    private var firstCursor: Int = 0
    private var secondCursor: Int = 1
    private var generator: RandomItemsGenerator? = null
    private var isInitialized: Boolean = false

    private val dataList: MutableList<UIModel> = mutableListOf(
        UIModel.SecondTypeModel(SecondItem(R.drawable.ic_baseline_audiotrack_24, "Tracks"), (0..Int.MAX_VALUE).random()),
        UIModel.SecondTypeModel(SecondItem(R.drawable.ic_baseline_album_24, "Albums"),(0..Int.MAX_VALUE).random()),
        UIModel.SecondTypeModel(SecondItem(R.drawable.ic_baseline_podcasts_24, "Podcasts"),(0..Int.MAX_VALUE).random()),
        UIModel.SecondTypeModel(SecondItem(R.drawable.ic_outline_file_download_24, "Downloaded"), (0..Int.MAX_VALUE).random()),
        UIModel.SecondTypeModel(
            SecondItem(
                R.drawable.ic_baseline_folder_open_24,
                "Tracks on your device"
            ), (0..Int.MAX_VALUE).random()
        ),
        UIModel.SecondTypeModel(SecondItem(R.drawable.ic_baseline_child_care_24, "For kids"),(0..Int.MAX_VALUE).random()),
    )

    fun initRepository(resources: Resources, itemsAmount: Int) {
        generator = RandomItemsGenerator(resources)
        dataList.addAll(generator!!.generateRandomList(itemsAmount))
        dataList.sortWith(ModelsComparator.comparator())
        for (i in dataList.indices) {
            if (dataList[i] is UIModel.FirstTypeModel && dataList[i + 1] is UIModel.SecondTypeModel) {
                firstCursor = i + 1
            }
            if (dataList[i] is UIModel.HeaderModel && dataList[i + 1] is UIModel.ThirdTypeModel) {
                secondCursor = i + 1
            }
        }
        isInitialized = true
    }

    fun getDataList(context: Context): List<UIModel> {
        if (!isInitialized) {
            Toast.makeText(context, "Repository should be initialized first! ", Toast.LENGTH_LONG)
                .show()
        } else {
            return dataList
        }
        return listOf()
    }

    fun addRandomItem(): Int {
        Log.d("cursors", " cursorFirst: " + firstCursor + " cursorSecond: " + secondCursor)
        var randomPosition = 0
        when (val generatedItem = generateRandomItem()) {
            is UIModel.FirstTypeModel -> {
                randomPosition = (0..firstCursor).random()
                dataList.add(randomPosition, generatedItem)
                firstCursor++
                secondCursor++
            }
            is UIModel.SecondTypeModel -> {
                randomPosition = (firstCursor + 1 until secondCursor).random()
                dataList.add(randomPosition, generatedItem)
                secondCursor++
            }
            else -> {
                randomPosition = (secondCursor + 1 until dataList.size).random()
                dataList.add(randomPosition, generatedItem)
            }
        }
        return randomPosition
    }

    fun addItem(item: UIModel, position: Int) {
        when (item) {
            is UIModel.FirstTypeModel -> {
                dataList.add(position, item)
                firstCursor++
            }
            is UIModel.SecondTypeModel -> {
                dataList.add(position, item)
                secondCursor++
            }
            else -> {
                dataList.add(position, item)
            }
        }
    }

    fun deleteItem(index: Int) {
        when (dataList[index]) {
            is UIModel.FirstTypeModel -> {
                dataList.removeAt(index)
                firstCursor--
                secondCursor--
            }
            is UIModel.SecondTypeModel -> {
                dataList.removeAt(index)
                secondCursor--
            }
            is UIModel.ThirdTypeModel -> {
                dataList.removeAt(index)
            }
            else -> {}
        }
    }

    private fun generateRandomItem(): UIModel {
        return when ((0..2).random()) {
            0 -> UIModel.FirstTypeModel(generator!!.generateFirstItem(), (0..Int.MAX_VALUE).random())
            1 -> UIModel.SecondTypeModel(generator!!.generateSecondItem(), (0..Int.MAX_VALUE).random())
            else -> {
                UIModel.ThirdTypeModel(generator!!.generateThirdItem(), (0..Int.MAX_VALUE).random())
            }
        }
    }
}