package com.example.myapplication.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.network.data.SingleNews

class NewsAdapter(private val news: MutableList<SingleNews>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage: ImageView
        val newsBody: TextView
        val newsTitle: TextView
        val newsTime: TextView

        init {
            newsBody = view.findViewById(R.id.news_body)
            newsImage = view.findViewById(R.id.news_image)
            newsTitle = view.findViewById(R.id.news_title)
            newsTime = view.findViewById(R.id.news_time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_item_holder, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            newsTitle.text = news[position].commenter
            newsBody.text = news[position].comment
            newsTime.text = news[position].date
        }
    }

    fun addItems(moreNews: List<SingleNews>) {
        news.addAll(moreNews)
    }

    override fun getItemCount() = news.size
}
