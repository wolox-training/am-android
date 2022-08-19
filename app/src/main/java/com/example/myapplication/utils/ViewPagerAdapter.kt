package com.example.myapplication.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.NewsEmptyFragment
import com.example.myapplication.ui.NewsTabFragment
import com.example.myapplication.ui.ProfileTabFragment
import kotlin.random.Random

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return when (position) {
            0 -> {
                val newsRandomSelector = Random.nextBoolean()
                if (newsRandomSelector) {
                    NewsTabFragment()
                } else {
                    NewsEmptyFragment()
                }
            }
            else ->  ProfileTabFragment()
        }
    }
}
