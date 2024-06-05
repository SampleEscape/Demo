package com.escape.demo.list.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.escape.demo.databinding.ItemCategoryBinding
import com.escape.demo.model.CategoryItem
import com.escape.demo.utils.viewholder.LifecycleViewHolder

class CategoryItemViewHolder(
    parent: ViewGroup,
    private val listener: CategoryItemListener,
    private val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
): LifecycleViewHolder<CategoryItem>(binding.root) {

    override fun bind(item: CategoryItem) {
        super.bind(item)
        binding.text.text = item.title
    }

    override fun onSetUpClickEvent(item: CategoryItem) {
        binding.root.setOnClickListener {
            listener.onClick(item, adapterPosition)
        }
    }

    override fun onCollectState(lifecycleOwner: LifecycleOwner, item: CategoryItem) {
        listener.isLastSelectedFlow(item).collect (
            lifecycleOwner = lifecycleOwner,
            default = false
        ) { isLastSelected ->
            binding.viewLastSelected.isVisible = isLastSelected
        }
    }
}