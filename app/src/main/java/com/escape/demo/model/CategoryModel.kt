package com.escape.demo.model

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil

fun Category(compose: CategoryBuilder.() -> Unit = {}) : List<CategoryGroup> {
    val builder = CategoryBuilder()
    builder.compose()
    return builder.build()
}

class CategoryGroup(
    val keyName: String,
    val title: String,
    val itemList: List<CategoryItem>
) {

    companion object {
        val Diff = CategoryGroupDiff()
    }
}

class CategoryGroupDiff : DiffUtil.ItemCallback<CategoryGroup>() {
    override fun areItemsTheSame(oldItem: CategoryGroup, newItem: CategoryGroup): Boolean {
        return oldItem.keyName == newItem.keyName
    }

    override fun areContentsTheSame(oldItem: CategoryGroup, newItem: CategoryGroup): Boolean {
        return oldItem.keyName == newItem.keyName
    }
}

open class CategoryItem(
    val keyName: String,
    val title: String,
) {
    lateinit var group: CategoryGroup

    open fun createFragment(): Fragment? = null

    companion object {
        val Diff = CategoryItemDiff()
    }
}

class CategoryItemDiff: DiffUtil.ItemCallback<CategoryItem>() {
    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem.keyName == newItem.keyName
    }

    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem.keyName == newItem.keyName
    }
}

class CategoryBuilder {
    private val groupList = mutableListOf<CategoryGroup>()

    fun build(): List<CategoryGroup> {
        return groupList.toList()
    }

    fun Group(
        keyName: String,
        title: String = keyName,
        compose: CategoryGroupBuilder.() -> Unit = {},
    ): CategoryGroup {
        if (groupList.find { it.keyName == keyName } != null) {
            throw RuntimeException("Category 안에 $keyName 는 이미 존재하는 Group 입니다")
        }

        val builder = CategoryGroupBuilder()
        builder.setKeyName(keyName)
        builder.setTitle(title)
        builder.compose()
        return builder.build().also { group ->
            if (group.itemList.isEmpty()) {
                throw RuntimeException("Group 안에 아이템이 1개 이상은 존재 해야 합니다.")
            }
            groupList.add(group)
        }
    }
}

class CategoryGroupBuilder {
    private var keyName: String = ""
    private var title: String = ""
    private val itemList = mutableListOf<CategoryItem>()

    fun build(): CategoryGroup {
        val group = CategoryGroup(
            keyName = keyName,
            title = title,
            itemList = itemList.toList()
        )
        group.itemList.forEach {
            it.group = group
        }
        return group
    }

    fun setKeyName(keyName: String) {
        this.keyName = keyName
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun Item(
        keyName: String,
        title: String = keyName,
        createFragment: () -> Fragment? = { null },
    ) {
        if (itemList.find { it.keyName == keyName } != null) {
            throw RuntimeException("Group 안에 $keyName 는 이미 존재하는 Item 입니다")
        }

        itemList.add(object: CategoryItem(
            keyName = keyName,
            title = title,
        ) {
            override fun createFragment(): Fragment? {
                return createFragment()
            }
        })
    }
}