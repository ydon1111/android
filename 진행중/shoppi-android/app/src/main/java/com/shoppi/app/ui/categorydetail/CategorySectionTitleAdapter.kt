package com.shoppi.app.ui.categorydetail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.databinding.ItemTitleBinding
import com.shoppi.app.model.Title
import com.shoppi.app.ui.category.CategoryAdapter

class CategorySectionTitleAdapter :
    ListAdapter<Title, CategorySectionTitleAdapter.CategorySectionTitleViewHolder>(TitleDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorySectionTitleViewHolder {
        val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategorySectionTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategorySectionTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategorySectionTitleViewHolder(private val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: Title){
            binding.title = title
            binding.executePendingBindings()
        }
    }
}

class TitleDiffCallback : DiffUtil.ItemCallback<Title>() {
    override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean {
        return oldItem == newItem
    }


}