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

    var currentPage = 1

    private lateinit var binding: FragmentNewsTabBinding

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsTabBinding.inflate(inflater, container, false)
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsViewModel.getMoreNews(currentPage)
        val swipeToRefresh = binding.swipeToRefresh
        val adapter = NewsAdapter(mutableListOf())
        val layoutManager = LinearLayoutManager(context)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
        with(binding) {
            newsList.adapter = adapter
            newsList.addOnScrollListener(
                OnScrollListener(layoutManager) { newsViewModel.getMoreNews(currentPage) }
            )
        }
        newsResponseObserver()
        newsFailedRequestObserver()
    }

    private fun newsResponseObserver() {
        newsViewModel.newsResponse.observe(viewLifecycleOwner) {
            it?.let {
                currentPage++
                with(binding) {
                    if (newsList.visibility == View.GONE) {
                        noNewsError.visibility = View.GONE
                        newsList.visibility = View.VISIBLE
                    }
                    (newsList.adapter as NewsAdapter).addItems(it.page)
                    (newsList.adapter as NewsAdapter).notifyDataSetChanged()
                }
            }
        }
    }

    private fun newsFailedRequestObserver() {
        newsViewModel.newsFailedRequest.observe(viewLifecycleOwner) {
            it?.let {
                if (currentPage == 1) {
                    with(binding) {
                        if (newsList.visibility == View.VISIBLE) {
                            newsList.visibility = View.GONE
                            noNewsError.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}
