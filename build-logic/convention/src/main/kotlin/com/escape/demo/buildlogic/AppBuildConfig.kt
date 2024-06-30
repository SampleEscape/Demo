package com.escape.demo.buildlogic

internal typealias BuildValues = Map<AppBuildType, String>

internal class BuildValuesBuilder {

    private val valueMap = mutableMapOf<AppBuildType, String>()

    fun set(value: String) {
        AppBuildType.list.forEach { buildType ->
            valueMap[buildType] = value
        }
    }

    fun set(buildTypes: List<AppBuildType>, value: String) {
        buildTypes.forEach { buildType ->
            valueMap[buildType] = value
        }
    }

    fun set(buildType: AppBuildType, value: String) {
        valueMap[buildType] = value
    }

    fun setDefaultValue(value: String) {
        AppBuildType.list.forEach { buildType ->
            valueMap.putIfAbsent(buildType, value)
        }
    }

    fun build(): BuildValues  {
        return valueMap
    }
}

@Suppress("functionName")
internal fun BuildValues(
    defaultValue: String = "",
    configure: BuildValuesBuilder.() -> Unit
): BuildValues {
    val builder = BuildValuesBuilder()
    builder.configure()
    builder.setDefaultValue(defaultValue)
    return builder.build()
}

internal enum class ConfigType(
    val typeName: String,
) {
    STRING("String"),
    BOOLEAN("boolean"),
    LONG("long"),
    INT("int");
}

@Suppress("unused")
internal enum class AppBuildConfig(
    val type: ConfigType,
    val field: String,
    private val values: BuildValues,
) {
    CONFIG(ConfigType.STRING, "CONFIG", BuildValues {
        set(AppBuildType.DEBUG, "DEBUG")
        set(AppBuildType.BETA, "BETA")
        set(AppBuildType.RC, "RC")
        set(AppBuildType.RELEASE, "RELEASE")
    });

    fun getValue(buildType: AppBuildType): String {
        val raw = getRawValue(buildType)
        return if (type == ConfigType.STRING) {
            "\"${raw}\""
        } else {
            raw
        }
    }

    private fun getRawValue(buildType: AppBuildType): String {
        return values[buildType] ?: ""
    }

    companion object {
        val list = values()
    }
}

@Suppress("unused")
internal enum class AppManifestPlaceholder(
    val type: ConfigType,
    val field: String,
    private val values: BuildValues,
) {
    APP_PRODUCT_NAME(ConfigType.STRING, "APP_PRODUCT_NAME", BuildValues {
        set("방탈출")
    }),
    APP_BUILD_NAME(ConfigType.STRING, "APP_BUILD_NAME", BuildValues {
        set(AppBuildType.DEBUG, "[DEBUG]")
        set(AppBuildType.BETA, "[BETA]")
        set(AppBuildType.RC, "[RC]")
        set(AppBuildType.RELEASE, "")
    });

    fun getValue(buildType: AppBuildType): Any {
        val raw = getRawValue(buildType)
        return when(type) {
            ConfigType.INT -> raw.toInt()
            ConfigType.LONG -> raw.toLong()
            ConfigType.BOOLEAN -> raw.toBoolean()
            ConfigType.STRING -> raw
        }
    }

    private fun getRawValue(buildType: AppBuildType): String {
        return values[buildType] ?: ""
    }

    companion object {
        val list = values()
    }
}