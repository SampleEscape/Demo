package com.escape.demo.buildlogic

import com.android.builder.model.BuildType

enum class AppBuildType(
    val typeName: String,
    val isDebuggable: Boolean,
    val isMinifyEnabled: Boolean,
) {
    // 서버 등을 BETA 환경의 설정을 가짐
    // 난독화가 설정되지 않은 상태
    DEBUG(
        typeName = "debug",
        isDebuggable = true,
        isMinifyEnabled = false,
    ),

    // 서버 등을 BETA 환경의 설정을 가짐
    // 난독화가 설정된 상태
    BETA(
        typeName = "beta",
        isDebuggable = true,
        isMinifyEnabled = true,
    ),

    // 서버 등을 REAL 환경의 설정을 가짐
    // 난독화가 설정되지 않은 상태
    RC(
        typeName = "rc",
        isDebuggable = true,
        isMinifyEnabled = false,
    ),

    // 실제 프로덕스 환경
    // 개발자 도구등을 사용할 수 없는 상태
    // 난독화가 설정된 상태
    RELEASE(
        typeName = "release",
        isDebuggable = false,
        isMinifyEnabled = true,
    );

    val isExist
        get() = this == DEBUG || this == RELEASE

    companion object {
        val list = values().toList()

        val betas = listOf(DEBUG, BETA)
        val reals = listOf(RC, RELEASE)

        fun find(type: BuildType): AppBuildType? {
            return list.find { it.typeName == type.name }
        }
    }
}