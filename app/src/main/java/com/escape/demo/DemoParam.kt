package com.escape.demo

import android.content.Context
import android.content.Intent
import com.escape.demo.model.CategoryItem

data class DemoParam(
    val groupKeyName: String,
    val itemKeyName: String,
) {
    constructor(item: CategoryItem): this(
        groupKeyName = item.group.keyName,
        itemKeyName = item.keyName,
    )

    constructor(intent: Intent?): this (
        groupKeyName = getGroupKeyName(intent),
        itemKeyName = getItemKeyName(intent),
    )

    companion object {
        private const val EXTRA_GROUP_KEY_NAME = "Extra.CategoryGroup"
        private const val EXTRA_ITEM_KEY_NAME = "Extra.CategoryItem"

        fun getGroupKeyName(intent: Intent?): String {
            return intent?.getStringExtra(EXTRA_GROUP_KEY_NAME) ?: ""
        }

        fun getItemKeyName(intent: Intent?): String {
            return intent?.getStringExtra(EXTRA_ITEM_KEY_NAME) ?: ""
        }
    }

    fun buildIntent(context: Context): Intent {
        return Intent(context, DemoActivity::class.java).apply {
            putExtra(EXTRA_GROUP_KEY_NAME, groupKeyName)
            putExtra(EXTRA_ITEM_KEY_NAME, itemKeyName)
        }
    }
}