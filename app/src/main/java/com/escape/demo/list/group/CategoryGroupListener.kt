package com.escape.demo.list.group

import com.escape.demo.model.CategoryGroup
import kotlinx.coroutines.flow.Flow

interface CategoryGroupListener {

    fun isExpandFlow(item: CategoryGroup): Flow<Boolean>

    fun isLastSelectedFlow(item: CategoryGroup): Flow<Boolean>

    fun onClick(item: CategoryGroup, position: Int)
}