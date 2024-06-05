package com.escape.demo

import android.app.Application
import com.escape.demo.utils.logger.ComponentLogger

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ComponentLogger().initialize(this)
    }
}