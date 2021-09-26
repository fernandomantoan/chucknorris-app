package com.fernandomantoan.chucknorrisapp.searchscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandomantoan.chucknorrisapp.databinding.ListItemRecentSearchBinding
import com.fernandomantoan.chucknorrisapp.entity.RecentSearch

class RecentSearchAdapter(private val clickListener: RecentSearchClickListener):
    RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {

    interface RecentSearchClickListener {
        fun recentSearchClicked(recentSearch: RecentSearch)
    }

    private var recentSearches: List<RecentSearch> = listOf()

    fun setRecentSearches(recentSearches: List<RecentSearch>) {
        this.recentSearches = recentSearches
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemRecentSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentSearch = recentSearches[position]
        holder.recentSearchValue.text = recentSearch.name
    }

    override fun getItemCount(): Int {
        return recentSearches.size
    }

    inner class ViewHolder(binding: ListItemRecentSearchBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val recentSearchValue = binding.recentSearch

        override fun onClick(viewClicked: View?) {
            val position = bindingAdapterPosition
            if (position >= 0) {
                val recentSearch = recentSearches[position]
                clickListener.recentSearchClicked(recentSearch)
            }
        }

        init {
            binding.root.setOnClickListener(this)
        }
    }
}