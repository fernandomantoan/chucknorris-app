package com.fernandomantoan.chucknorrisapp.factsscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandomantoan.chucknorrisapp.R
import com.fernandomantoan.chucknorrisapp.databinding.ListItemFactBinding
import com.fernandomantoan.chucknorrisapp.entity.Fact

class FactsAdapter(private val clickListener: FactsClickListener):
    RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    interface FactsClickListener {
        fun factClicked(fact: Fact)
    }

    private var facts: List<Fact> = listOf()

    fun setFacts(facts: List<Fact>) {
        this.facts = facts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemFactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fact = facts[position]
        holder.factValue.text = fact.value
        holder.category.text = if (fact.categories.isEmpty())
            holder.itemView.context.getString(R.string.uncategorized)
        else
            fact.categories[0]
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    inner class ViewHolder(binding: ListItemFactBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val factValue = binding.factValue
        val category = binding.category

        override fun onClick(viewClicked: View?) {
            val position = bindingAdapterPosition
            if (position >= 0) {
                val fact = facts[position]
                clickListener.factClicked(fact)
            }
        }

        init {
            binding.root.setOnClickListener(this)
        }
    }
}