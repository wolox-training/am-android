package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNewsTabBinding
import com.example.myapplication.utils.NewsAdapter
import com.example.myapplication.utils.OnScrollListener
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
        newsViewModel.retrieveSavedUser(1)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            newsViewModel.retrieveSavedUser(1)
            swipeToRefresh.isRefreshing = false
        }
        emptyFieldsObserver()
    }

    private fun emptyFieldsObserver() {
        newsViewModel.newsResponse.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = NewsAdapter(it.page)
                val layoutManager = LinearLayoutManager(context)
                with(binding) {
                    newsList.adapter = adapter
                    newsList.addOnScrollListener(
                        OnScrollListener(layoutManager) { newsViewModel.retrieveSavedUser(it.nextPage) }
                    )
                }
            }
        }
    }
}
