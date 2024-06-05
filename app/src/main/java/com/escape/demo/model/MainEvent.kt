package com.escape.demo.model

sealed interface MainEvent {

    data class ScrollCategory(
        val group: CategoryGroup,
    ): MainEvent

    data class ExecuteItem(
        val item: CategoryItem,
    ): MainEvent
}