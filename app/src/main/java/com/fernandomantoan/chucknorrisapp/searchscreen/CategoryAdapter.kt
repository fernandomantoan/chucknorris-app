package com.fernandomantoan.chucknorrisapp.searchscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandomantoan.chucknorrisapp.databinding.ListItemCategoryBinding
import com.fernandomantoan.chucknorrisapp.entity.Category

class CategoryAdapter(private val clickListener: CategoryClickListener):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    interface CategoryClickListener {
        fun categoryClicked(category: Category)
    }

    private var categories: List<Category> = listOf()

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryValue.text = category.name
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(binding: ListItemCategoryBinding): RecyclerView.ViewHolder(binding.root),
            View.OnClickListener {

        val categoryValue = binding.category

        override fun onClick(viewClicked: View?) {
            val position = bindingAdapterPosition
            if (position >= 0) {
                val category = categories[position]
                clickListener.categoryClicked(category)
            }
        }

        init {
            categoryValue.setOnClickListener(this)
        }
    }
}