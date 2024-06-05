package com.escape.demo.data.setting

import android.content.Context
import androidx.core.content.edit

class LocalSettingDataSource(context: Context) {

    companion object {
        private const val KEY_LAST_SELECTED_GROUP = "Key.LastSelectedGroup"
        private const val KEY_LAST_SELECTED_ITEM = "Key.LastSelectedItem"

        private const val KEY_EXECUTE_ITEM = "Key.ExecuteItem"
    }

    private val preference = context.getSharedPreferences("main", Context.MODE_PRIVATE)

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun putString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    fun getLastSelectedGroup(): String {
        return getString(KEY_LAST_SELECTED_GROUP, "")
    }

    fun setLastSelectedGroup(keyName: String) {
        putString(KEY_LAST_SELECTED_GROUP, keyName)
    }

    fun getLastSelectedItem(): String {
        return getString(KEY_LAST_SELECTED_ITEM, "")
    }

    fun setLastSelectedItem(keyName: String) {
        putString(KEY_LAST_SELECTED_ITEM, keyName)
    }

    fun getExecuteItem(): String {
        return getString(KEY_EXECUTE_ITEM, "")
    }

    fun setExecuteItem(keyName: String) {
        putString(KEY_EXECUTE_ITEM, keyName)
    }
}