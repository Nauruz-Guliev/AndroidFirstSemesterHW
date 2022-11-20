package ru.kpfu.itis.hw_android_2022.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.hw_android_2022.models.City
import ru.kpfu.itis.hw_android_2022.databinding.CityItemBinding

class CitiesViewHolder(val binding: CityItemBinding) : ViewHolder(binding.root) {
    fun onBind(item: City) {
        binding.city = item
    }
    companion object {
        fun create(parent: ViewGroup) =
            CitiesViewHolder(
                CityItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}
