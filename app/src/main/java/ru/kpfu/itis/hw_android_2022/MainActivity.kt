package ru.kpfu.itis.hw_android_2022

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.itis.hw_android_2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val containerId: Int = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(
            containerId,
            MainFragment.createInstance(Bundle()),
            MainFragment.MAIN_FRAGMENT_TAG
        )
    }
}