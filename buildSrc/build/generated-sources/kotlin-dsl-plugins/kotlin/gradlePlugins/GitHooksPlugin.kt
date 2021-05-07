package gradlePlugins


/**
 * Precompiled [git-hooks.gradle.kts][gradlePlugins.Git_hooks_gradle] script plugin.
 *
 * @see gradlePlugins.Git_hooks_gradle
 */
class GitHooksPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("gradlePlugins.Git_hooks_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
