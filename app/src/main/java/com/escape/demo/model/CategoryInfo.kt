package com.escape.demo.model

import com.escape.demo.page.group1.item1.Item1TestFragment
import com.escape.demo.page.group1.item2.Item2TestFragment

object CategoryInfo {

    val list = Category {
        Group("Group1") {
            Item("Item1") {
                Item1TestFragment()
            }
            Item("Item2") {
                Item2TestFragment()
            }
        }
        Group("Group2") {
            Item("Item1") {
                Item1TestFragment()
            }
            Item("Item2") {
                Item2TestFragment()
            }
        }
        Group("Group3") {
            Item("Item1") {
                Item1TestFragment()
            }
            Item("Item2") {
                Item2TestFragment()
            }
        }
    }

    fun findByKeyName(groupKeyName: String, itemKeyName: String): CategoryItem? {
        val group = list.find { it.keyName == groupKeyName } ?: return null
        return group.itemList.find { it.keyName == itemKeyName }
    }
}