package com.escape.demo.data.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingRepository(
    private val localSettingDataSource: LocalSettingDataSource,
) {
    private val _lastSelectedGroupFlow = MutableStateFlow("")
    val lastSelectedGroupFlow = _lastSelectedGroupFlow.asStateFlow()
    val lastSelectedGroup
        get() = lastSelectedGroupFlow.value

    fun setLastSelectedGroup(keyName: String) {
        if (lastSelectedGroup == keyName) {
            return
        }
        localSettingDataSource.setLastSelectedGroup(keyName)
        _lastSelectedGroupFlow.value = keyName
    }

    private val _lastSelectedItemFlow = MutableStateFlow("")
    val lastSelectedItemFlow = _lastSelectedItemFlow.asStateFlow()
    val lastSelectedItem
        get() = lastSelectedItemFlow.value

    fun setLastSelectedItem(keyName: String) {
        if (lastSelectedItem == keyName) {
            return
        }
        localSettingDataSource.setLastSelectedItem(keyName)
        _lastSelectedItemFlow.value = keyName
    }

    val executeItem
        get() = localSettingDataSource.getExecuteItem()

    fun setExecuteItem(keyName: String) {
        localSettingDataSource.setExecuteItem(keyName)
    }

    init {
        _lastSelectedGroupFlow.value = localSettingDataSource.getLastSelectedGroup()
        _lastSelectedItemFlow.value = localSettingDataSource.getLastSelectedItem()
    }
}