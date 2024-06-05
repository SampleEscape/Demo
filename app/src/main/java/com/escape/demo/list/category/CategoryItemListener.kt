package com.escape.demo.list.category

import com.escape.demo.model.CategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryItemListener {

    fun isLastSelectedFlow(item: CategoryItem): Flow<Boolean>

    fun onClick(item: CategoryItem, position: Int)
}