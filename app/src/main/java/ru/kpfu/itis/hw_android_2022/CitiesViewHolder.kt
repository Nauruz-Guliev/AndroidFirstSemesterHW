package ru.kpfu.itis.hw_android_2022

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.hw_android_2022.dao.model.City
import ru.kpfu.itis.hw_android_2022.databinding.CityItemBinding

class CitiesViewHolder(val binding: CityItemBinding) : ViewHolder(binding.root) {
    fun onBind(item: City) {
        Log.d("VALUES", item.name)
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