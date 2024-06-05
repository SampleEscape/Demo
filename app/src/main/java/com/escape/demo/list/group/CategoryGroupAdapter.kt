package com.escape.demo.list.group

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.escape.demo.list.category.CategoryItemListener
import com.escape.demo.model.CategoryGroup

class CategoryGroupAdapter(
    private val groupListener: CategoryGroupListener,
    private val itemListener: CategoryItemListener,
) : ListAdapter<CategoryGroup, CategoryGroupViewHolder>(CategoryGroup.Diff){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryGroupViewHolder {
        return CategoryGroupViewHolder(parent, groupListener, itemListener)
    }

    override fun onBindViewHolder(holder: CategoryGroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getPosition(item: CategoryGroup): Int {
        return currentList.indexOfFirst { it.keyName == item.keyName }
    }
}