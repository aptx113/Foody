import com.android.build.gradle.LibraryExtension
import com.danteyu.studio.foody.config.*
import com.danteyu.studio.foody.config.LibsConst.TEST
import com.danteyu.studio.foody.configureFlavors
import com.danteyu.studio.foody.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(PluginsConst.ANDROID_LIBRARY_ID)
                apply(PluginsConst.KOTLIN_ANDROID_ID)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = DefaultConfigs.TARGET_SDK
                configureFlavors(this)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary(LibsConst.JUNIT4).get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }
            dependencies {
                implementAndroidTest(kotlin(TEST))
                implementTest(kotlin(TEST))
            }
        }
    }
}
