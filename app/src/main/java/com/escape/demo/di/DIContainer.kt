package com.escape.demo.di

import android.content.Context
import com.escape.demo.data.setting.LocalSettingDataSource
import com.escape.demo.data.setting.SettingRepository

object DIContainer {

    private var settingRepository: SettingRepository? = null

    private fun createSettingRepository(context: Context): SettingRepository {
        val localDataSource = LocalSettingDataSource(context)
        return SettingRepository(localDataSource)
    }

    fun getSettingRepository(context: Context): SettingRepository {
        return settingRepository ?: createSettingRepository(context).also {
            settingRepository = it
        }
    }
}