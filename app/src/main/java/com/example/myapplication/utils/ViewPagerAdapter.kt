package com.example.myapplication.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentTabBinding

class ViewPagerAdapter(
    private val context: Context,
    private val labelList: MutableList<String>,
    private val colorList: MutableList<String>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = FragmentTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val label = labelList[position]
        val color = colorList[position]
        holder.bind(label, color)
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

    class ViewPagerHolder(private var itemHolderBinding: FragmentTabBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {
        fun bind(label: String, color: String) {
            itemHolderBinding.label.text = label
            itemHolderBinding.root.setBackgroundColor(Color.parseColor(color))
        }
    }
}
