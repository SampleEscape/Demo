import com.android.build.gradle.LibraryExtension
import com.escape.demo.buildlogic.configureAndroid
import com.escape.demo.buildlogic.configureAndroidCompose
import com.escape.demo.buildlogic.configureComposeKotlin
import com.escape.demo.buildlogic.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }

            extensions.configure<ComposeCompilerGradlePluginExtension> {
                configureAndroidCompose(this)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                configureComposeKotlin(this)
            }
        }
    }
}