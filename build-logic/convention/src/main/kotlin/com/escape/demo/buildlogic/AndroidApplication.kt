package com.escape.demo.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

internal fun configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = ProjectConfigurations.compileSdk
        defaultConfig {
            minSdk = ProjectConfigurations.minSdk
        }

        compileOptions {
            sourceCompatibility = ProjectConfigurations.javaVer
            targetCompatibility = ProjectConfigurations.javaVer
        }
    }
}

internal fun configureAndroidCompose(
    extension: ComposeCompilerGradlePluginExtension,
) {
    extension.apply {
        enableStrongSkippingMode.set(true)
        includeSourceInformation.set(true)
    }
}

internal fun Project.configureKotlin(
    extension: KotlinAndroidProjectExtension
) {
    val warningsAsErrors: String? by project
    extension.apply {
        compilerOptions {
            jvmTarget.set(ProjectConfigurations.jvmTarget)
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.addAll(
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            )
        }
    }
}

internal fun Project.configureComposeKotlin(
    extension: KotlinAndroidProjectExtension
) {
    val warningsAsErrors: String? by project
    configureKotlin(extension)
    extension.apply {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    project.layout.buildDirectory.get() + "/compose_metrics",
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    project.layout.buildDirectory.get() + "/compose_metrics",
            )
        }
    }
}
