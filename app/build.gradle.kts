
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
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
}