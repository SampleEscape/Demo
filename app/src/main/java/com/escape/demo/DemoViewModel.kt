package com.escape.demo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.escape.demo.data.setting.SettingRepository
import com.escape.demo.di.DIContainer
import com.escape.demo.extension.getApplication
import com.escape.demo.model.CategoryItem

class DemoViewModel(
    private val repository: SettingRepository,
) : ViewModel() {

    companion object {
        fun factory() = viewModelFactory {
            initializer {
                val context = getApplication<Context>()
                val repository = DIContainer.getSettingRepository(context)
                DemoViewModel(repository)
            }
        }
    }

    fun setLastSelectedItem(item: CategoryItem) {
        repository.setLastSelectedGroup(item.group.keyName)
        repository.setLastSelectedItem(item.keyName)
        repository.setExecuteItem(item.keyName)
    }

    fun clearExecuteItem() {
        repository.setExecuteItem("")
    }
}