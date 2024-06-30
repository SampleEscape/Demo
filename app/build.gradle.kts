
plugins {
    id ("escape.android.application")
}

android {
    namespace = "com.escape.demo"

    defaultConfig {
        applicationId = "com.escape.demo"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding {
            enable = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.bundles.lifecycle.compose)
}