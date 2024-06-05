package com.escape.demo.list.category

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.escape.demo.model.CategoryItem

class CategoryItemAdapter(
    private val listener: CategoryItemListener,
): ListAdapter<CategoryItem, CategoryItemViewHolder>(CategoryItem.Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}