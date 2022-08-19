package com.example.myapplication.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsTabBinding
import com.example.myapplication.databinding.FragmentProfileTabBinding
import com.example.myapplication.databinding.FragmentTabBinding

class ViewPagerAdapter(
    private val labelList: MutableList<String>
    ) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return when (viewType) {
            R.layout.fragment_home_page -> {
                val binding = FragmentNewsTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder1(binding)
            }
            else -> {
                val binding = FragmentProfileTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder2(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        is ViewHolder1 -> {
            var myDataset = arrayOf("one", "two", "three", "four", "five")
            var viewManager = LinearLayoutManager(ctx)
            var viewAdapter = PkgSetTypeFragment.PackageTypeAdapter(ctx, myDataset)

            viewManager.orientation = LinearLayoutManager.VERTICAL
            holder.id_pkg_set_type_list_rv.setHasFixedSize(true)
            holder.id_pkg_set_type_list_rv.layoutManager = viewManager
            holder.id_pkg_set_type_list_rv.adapter = viewAdapter

            viewAdapter.notifyDataSetChanged()

            holder.imageButton.setOnClickListener(View.OnClickListener {

            })
        }
        is ViewHolder2 -> holder.myTextView.text = mData[position]
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

    class ViewPagerHolder(private var itemHolderBinding: FragmentTabBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {
        fun bind(label: String) {
            itemHolderBinding.label.text = label
        }
    }
}
