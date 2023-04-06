import com.android.build.api.dsl.ApplicationExtension
import com.danteyu.studio.foody.config.DefaultConfigs
import com.danteyu.studio.foody.config.PluginsConst
import com.danteyu.studio.foody.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(PluginsConst.ANDROID_APPLICATION_ID)
                apply(PluginsConst.KOTLIN_ANDROID_ID)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = DefaultConfigs.TARGET_SDK
            }
        }
    }
}
