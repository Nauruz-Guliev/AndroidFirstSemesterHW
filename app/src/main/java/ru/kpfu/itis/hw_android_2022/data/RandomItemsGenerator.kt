package ru.kpfu.itis.hw_android_2022.data

import android.content.res.Resources
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.models.FirstItem
import ru.kpfu.itis.hw_android_2022.models.SecondItem
import ru.kpfu.itis.hw_android_2022.models.ThirdItem
import ru.kpfu.itis.hw_android_2022.models.UIModel

class RandomItemsGenerator(private val resources: Resources) {

    fun generateRandomList(size: Int): MutableList<UIModel> {
        val dataList: MutableList<UIModel> = mutableListOf()
        val firstRange = (size *4)/ 17
        val secondRange = (size * 7) / 17
        val thirdRange = (size * 6) / 17
        for (i in 0..firstRange) {
            dataList.add(UIModel.FirstTypeModel(generateFirstItem(), i))
        }
        for (i in 0..secondRange) {
            dataList.add(UIModel.SecondTypeModel(generateSecondItem(), i) )
        }
        for (i in 0..thirdRange) {
            dataList.add(UIModel.ThirdTypeModel(generateThirdItem(), i))
        }
        dataList.add(UIModel.HeaderModel(getStringResource(R.string.recently_listened, ""),0))
        return dataList
    }

    fun generateFirstItem(): FirstItem {
        return FirstItem(
            randomPrimaryHeaders[(randomPrimaryHeaders.indices).random()],
            getStringResource(R.string.duration, randomNonNullNumber(100)),
            randomSecondaryHeaders(),
            resources.getString(randomImageUrls[(randomImageUrls.indices).random()]),
        )
    }

    fun generateSecondItem(): SecondItem {
        return SecondItem(
            randomDrawableIds[(randomDrawableIds.indices).random()],
            primaryCategories[(primaryCategories.indices).random()],
        )
    }

    fun generateThirdItem(): ThirdItem {
        return ThirdItem(
            resources.getString(randomImageUrls[(randomImageUrls.indices).random()]),
            getStringResource(R.string.duration, randomNonNullNumber(40)),
            randomPrimaryHeaders[(randomPrimaryHeaders.indices).random()],
            randomSecondaryHeaders()
        )
    }

    private val primaryCategories: List<String> = listOf(
        getStringResource(R.string.metal, argument = randomNonNullNumber(30)),
        getStringResource(R.string.rock, argument = randomNonNullNumber(30)),
        getStringResource(R.string.country, argument = randomNonNullNumber(30)),
        getStringResource(R.string.EDM, argument = randomNonNullNumber(30))
    )

    private val randomPrimaryHeaders: List<String> = listOf(
        getStringResource(R.string.my_playlist, argument = randomNonNullNumber(50)),
        getStringResource(R.string.all_time_best, argument = randomNonNullNumber(50)),
        getStringResource(R.string.my_favorites, argument = randomNonNullNumber(50)),
    )

    private fun randomNonNullNumber(endRange: Int): String = (2..endRange).random().toString()
    private fun getStringResource(resID: Int, argument: String) =
        resources.getString(resID, argument)

    private fun randomSecondaryHeaders() =
        getStringResource(R.string.tracks, randomNonNullNumber(70))

    private val randomImageUrls: List<Int> = listOf(
        R.string.album_image_url1,
        R.string.album_image_url2,
        R.string.album_image_url3,
        R.string.album_image_url4,
        R.string.album_image_url5,
        R.string.album_image_url6,
        R.string.album_image_url7
    )

    private val randomDrawableIds: List<Int> = listOf(
        R.drawable.ic_baseline_emoji_emotions_24,
        R.drawable.ic_baseline_downhill_skiing_24,
        R.drawable.ic_baseline_fitness_center_24,
        R.drawable.ic_baseline_directions_bike_24,
        R.drawable.ic_baseline_face_2_24
    )
}