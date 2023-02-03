package ru.kpfu.itis.hw_android_2022.viewPagerComponents

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.hw_android_2022.MainActivity

class ViewPagerAdapter(
    activity: MainActivity,
    private val fragmentList: ArrayList<Fragment>
) : FragmentStateAdapter(activity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}