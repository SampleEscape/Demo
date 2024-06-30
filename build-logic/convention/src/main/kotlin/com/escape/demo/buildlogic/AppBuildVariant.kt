package com.escape.demo.buildlogic

import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.Action
import org.gradle.api.Project

internal fun Project.configureBuildVariant(
    appExtension: AppExtension,
) {
    appExtension.apply {
        buildFeatures.buildConfig = true

        buildTypes {
            AppBuildType.list.forEach { buildType ->
                val configureAction = Action<BuildType> {
                    isDebuggable = buildType.isDebuggable
                    isMinifyEnabled =  buildType.isMinifyEnabled
                }

                if (buildType.isExist) {
                    getByName(buildType.typeName, configureAction)
                } else {
                    create(buildType.typeName, configureAction)
                }
            }
        }

        applicationVariants.all {
            val buildType = AppBuildType.find(buildType) ?: return@all

            AppBuildConfig.list.forEach { config ->
                val value = config.getValue(buildType)
                buildConfigField(config.type.typeName, config.field, value)
            }

            AppManifestPlaceholder.list.forEach { placeholder ->
                val value = placeholder.getValue(buildType)
                mergedFlavor.manifestPlaceholders[placeholder.field] = value
            }
        }
    }
}