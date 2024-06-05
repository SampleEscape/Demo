package com.escape.demo.list.group

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.escape.demo.R
import com.escape.demo.databinding.ItemCategoryGroupBinding
import com.escape.demo.extension.dp
import com.escape.demo.list.category.CategoryItemAdapter
import com.escape.demo.list.category.CategoryItemListener
import com.escape.demo.model.CategoryGroup
import com.escape.demo.utils.viewholder.LifecycleViewHolder

class CategoryGroupViewHolder(
    parent: ViewGroup,
    private val groupListener: CategoryGroupListener,
    private val itemListener: CategoryItemListener,
    private val binding: ItemCategoryGroupBinding = ItemCategoryGroupBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
): LifecycleViewHolder<CategoryGroup>(binding.root) {

    companion object {
        private const val MIN_LIST_HEIGHT = 1
        private val LIST_ITEM_HEIGHT = 48.dp
    }

    private val itemAdapter = CategoryItemAdapter(itemListener)
    private var listHeight: Int? = null
    init {
        itemAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                listHeight = null
            }
        })
        binding.itemList.adapter = itemAdapter
    }

    override fun bind(item: CategoryGroup) {
        super.bind(item)

        binding.text.text = item.title
        itemAdapter.submitList(item.itemList)
    }

    override fun onSetUpClickEvent(item: CategoryGroup) {
        binding.containerTitle.setOnClickListener {
            groupListener.onClick(item, adapterPosition)
        }
    }

    override fun onCollectState(lifecycleOwner: LifecycleOwner, item: CategoryGroup) {
        groupListener.isExpandFlow(item).collect(
            lifecycleOwner = lifecycleOwner
        ) { isExpand, init ->
            val btnIcon = when (isExpand) {
                true -> R.drawable.icon_expand
                false -> R.drawable.icon_collapsed
            }
            binding.btnList.setImageResource(btnIcon)

            val typeface = if (isExpand) Typeface.BOLD else Typeface.NORMAL
            binding.text.setTypeface(null, typeface)

            val start = binding.itemList.height
            val end = when (isExpand) {
                true -> getListHeight(binding.itemList)
                false -> MIN_LIST_HEIGHT
            }
            if (init) {
                binding.itemList.updateLayoutParams { height = end }
            } else {
                binding.itemList.animate()
                    .setDuration(300L)
                    .setInterpolator(DecelerateInterpolator())
                    .setUpdateListener {
                        binding.itemList.updateLayoutParams {
                            height = (start + (end - start) * it.animatedFraction).toInt()
                        }
                    }.start()
            }
        }

        groupListener.isLastSelectedFlow(item).collect (
            lifecycleOwner = lifecycleOwner,
            default = false
        ) { isLastSelected ->
            binding.viewLastSelected.isVisible = isLastSelected
        }
    }

    private fun getListHeight(view: RecyclerView): Int {
        return listHeight ?: calculateListHeight(view).also {
            listHeight = it
        }
    }

    private fun calculateListHeight(view: RecyclerView): Int {
        val itemCount = view.adapter?.itemCount ?: 0
        if (itemCount == 0) {
            return MIN_LIST_HEIGHT
        }
        return itemCount * LIST_ITEM_HEIGHT
    }
}