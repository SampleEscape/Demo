import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.escape.demo.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "escape.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "escape.android.compose"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }
        register("androidLibrary") {
            id = "escape.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}