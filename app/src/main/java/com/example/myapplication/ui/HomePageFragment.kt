package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomePageBinding
import com.example.myapplication.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "News"
                    tab.icon = ResourcesCompat.getDrawable(
                        activity!!.resources,
                        R.drawable.ic_news_list_off,
                        null
                    )
                }
                1 -> {
                    tab.text = "Profile"
                    tab.icon = ResourcesCompat.getDrawable(
                        activity!!.resources,
                        R.drawable.ic_profile_on,
                        null
                    )
                }
            }
        }.attach()
    }
}
