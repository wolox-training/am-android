package com.example.myapplication.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomePageBinding
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        setupViewPager2()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "News"
                    tab.icon = ResourcesCompat.getDrawable(activity!!.resources, R.drawable.ic_news_list_off, null)
                }
                1 -> {
                    tab.text = "Profile"
                    tab.icon = ResourcesCompat.getDrawable(activity!!.resources, R.drawable.ic_profile_on, null)
                }
            }
        }.attach()
    }

    private fun setupViewPager2() {
        val list: MutableList<String> = ArrayList()
        list.add("This is your News Tab")
        list.add("This is your Profile Tab")

        // Set adapter to viewPager.
        binding.viewPager.adapter = ViewPagerAdapter(list)
    }
}
