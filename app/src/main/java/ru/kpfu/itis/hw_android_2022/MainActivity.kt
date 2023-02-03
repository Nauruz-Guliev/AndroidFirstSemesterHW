package ru.kpfu.itis.hw_android_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.kpfu.itis.hw_android_2022.data.RandomItemsGenerator
import ru.kpfu.itis.hw_android_2022.databinding.ActivityMainBinding
import ru.kpfu.itis.hw_android_2022.fragments.FragmentFirst
import ru.kpfu.itis.hw_android_2022.fragments.FragmentSecond
import ru.kpfu.itis.hw_android_2022.viewPagerComponents.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
        initRepository()
    }

    private fun initRepository(){
        Repository.initRepository(resources, 15)
    }
    private fun initViewPager(){
        val bundle = Bundle()
        val listOfFragments = arrayListOf<Fragment>(
            FragmentFirst.newInstance(bundle),
            FragmentSecond.newInstance(bundle)
        )
        val adapter = ViewPagerAdapter(this,listOfFragments)
        with(binding){
            viewPager.adapter = adapter
            TabLayoutMediator(tbLayout, viewPager) {
                    tab, position->
                /* this didn't work for some unknown reason???
                is it because 'when' doesn't return anything??
                tab.text.apply {
                    when (position) {
                        1 -> "First fragment"
                        2 -> "Second fragment"
                    }
                }
                 */
                when(position) {
                    0-> tab.text = "First fragment"
                    1-> tab.text = "Second fragment"
                }
            }.attach()
        }
    }
}