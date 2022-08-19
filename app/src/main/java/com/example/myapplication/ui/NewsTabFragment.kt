package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentNewsTabBinding
import com.example.myapplication.utils.NewsAdapter

class NewsTabFragment : Fragment() {

    private lateinit var binding: FragmentNewsTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
        val adapter = NewsAdapter(listOf("First News", "Second News", "Third News", "Fourth News", "Fifth News"))
        binding.newsList.adapter = adapter
    }
}
