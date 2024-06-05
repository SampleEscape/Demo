package com.escape.demo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.escape.demo.data.setting.SettingRepository
import com.escape.demo.di.DIContainer
import com.escape.demo.extension.getApplication
import com.escape.demo.model.CategoryGroup
import com.escape.demo.model.CategoryInfo
import com.escape.demo.model.CategoryItem
import com.escape.demo.model.MainEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: SettingRepository,
) : ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer {
                val context = getApplication<Context>()
                val repository = DIContainer.getSettingRepository(context)
                MainViewModel(repository)
            }
        }
    }

    private val _eventChannel = Channel<MainEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val currentExpandedGroup = MutableStateFlow("")

    fun isLastSelectedFlow(item: CategoryGroup) = repository.lastSelectedGroupFlow
        .map { it == item.keyName }
        .distinctUntilChanged()

    fun isLastSelectedFlow(item: CategoryItem) = repository.lastSelectedItemFlow
        .map { it == item.keyName }
        .distinctUntilChanged()

    init {
        val lastSelectedGroup = repository.lastSelectedGroup
        val list = CategoryInfo.list
        val group = list.find { it.keyName == lastSelectedGroup } ?: list.first()
        setExpandGroup(group)
        viewModelScope.launch {
            _eventChannel.send(MainEvent.ScrollCategory(group))
        }

        val item = CategoryInfo.findByKeyName(
            groupKeyName = lastSelectedGroup,
            itemKeyName = repository.executeItem
        )
        if (item != null) {
            viewModelScope.launch {
                _eventChannel.send(MainEvent.ExecuteItem(item))
            }
        }
    }

    fun setExpandGroup(item: CategoryGroup) {
        val oldExpandGroup = currentExpandedGroup.value
        val newExpandGroup = if (oldExpandGroup != item.keyName) item.keyName else ""
        repository.setLastSelectedGroup(item.keyName)
        currentExpandedGroup.value = newExpandGroup
    }

    fun isExpandFlow(item: CategoryGroup) = currentExpandedGroup.map {
        it == item.keyName
    }.distinctUntilChanged()
}