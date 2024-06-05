package com.escape.demo.extension

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

fun <T> CreationExtras.getApplication(): T {
    return checkNotNull(get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY) as? T)
}