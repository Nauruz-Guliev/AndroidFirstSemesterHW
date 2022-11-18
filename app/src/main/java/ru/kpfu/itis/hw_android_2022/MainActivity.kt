package ru.kpfu.itis.hw_android_2022

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.kpfu.itis.hw_android_2022.adapters.ViewPagerAdapter
import ru.kpfu.itis.hw_android_2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var viewPagerAdapter: ViewPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        with(binding) {
            TabLayoutMediator(tabLayout, viewPager) {
                    tab, position ->
                when(position) {
                    0-> tab.text = "Cities"
                    1-> tab.text = "Camera"
                }
            }.attach()
        }
    }

    private fun initViewPager() {
        val commonBundle = Bundle()
        val fragmentsList = listOf<Fragment>(
            CitiesFragment.createInstance(commonBundle),
            CameraFragment.createInstance(commonBundle),
            )
        viewPagerAdapter = ViewPagerAdapter(
            activity = this,
            fragments = fragmentsList
        )
        binding.viewPager.adapter = viewPagerAdapter
    }


}