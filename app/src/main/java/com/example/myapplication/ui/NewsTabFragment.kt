package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsTabBinding
import com.example.myapplication.utils.NewsAdapter
import com.example.myapplication.utils.OnScrollListener
import com.example.myapplication.vm.LoginViewModel
import com.example.myapplication.vm.NewsViewModel

class NewsTabFragment : Fragment() {

    private lateinit var binding: FragmentNewsTabBinding

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsTabBinding.inflate(inflater, container, false)
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.retrieveSavedUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
        emptyFieldsObserver()
    }

    private fun emptyFieldsObserver() {
        newsViewModel.newsResponse.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = NewsAdapter(it.page)
                val dataList = mutableListOf(it.page.size)
                val layoutManager = LinearLayoutManager(context)
                with(binding) {
                    newsList.adapter = adapter
                    newsList.addOnScrollListener(OnScrollListener(layoutManager, adapter, dataList))
                }
            }
        }
    }
}
