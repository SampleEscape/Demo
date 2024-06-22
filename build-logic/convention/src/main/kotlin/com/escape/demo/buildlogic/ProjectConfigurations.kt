package com.escape.demo.buildlogic

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object ProjectConfigurations {
    const val compileSdk = 34
    const val minSdk = 30
    const val targetSdk = 34
    val javaVer = JavaVersion.VERSION_17
    val jvmTarget = JvmTarget.JVM_17
}
