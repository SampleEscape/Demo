import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppExtension
import com.escape.demo.buildlogic.ProjectConfigurations
import com.escape.demo.buildlogic.configureAndroid
import com.escape.demo.buildlogic.configureAndroidCompose
import com.escape.demo.buildlogic.configureBuildVariant
import com.escape.demo.buildlogic.configureComposeKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)

                defaultConfig {
                    targetSdk = ProjectConfigurations.targetSdk
                }
            }

            extensions.configure<ComposeCompilerGradlePluginExtension> {
                configureAndroidCompose(this)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                configureComposeKotlin(this)
            }

            extensions.configure<AppExtension> {
                configureBuildVariant(this)
            }
        }
    }
}