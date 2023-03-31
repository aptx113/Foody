import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.danteyu.studio.foody.config.PluginsConst
import com.danteyu.studio.foody.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(PluginsConst.JACOCO_ID)
                apply(PluginsConst.ANDROID_APPLICATION_ID)
            }
            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}
