/**
 * Configuration of all gradle build plugins
 */
object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_DYNAMIC_FEATURE = "com.android.dynamic-feature"
    const val ANDROID_LIBRARY = "com.android.library"

    const val COMMON_ANDROID_LIBRARY = "commons.android-library"
    const val COMMON_DYNAMIC_FEATURE = "commons.android-dynamic-feature"
    const val COMMON_KOTLIN_LIBRARY = "commons.kotlin-library"
    const val JAVA_LIBRARY = "java-library"

    const val HILT_ANDROID = "dagger.hilt.android.plugin"
    const val KOTLIN = "kotlin"
    const val KOTLIN_ANDROID = "android"
    const val KOTLIN_KAPT = "kapt"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val NAV_SAFEARGS = "androidx.navigation.safeargs.kotlin"

    const val DETEKT = "io.gitlab.arturbosch.detekt"
    const val DOKKA = "org.jetbrains.dokka"
    const val GIT_HOOKS = "gradlePlugins.git-hooks"
    const val GRADLE_VERSIONS_PLUGIN = "com.github.ben-manes.versions"
    const val KTLINT = "org.jlleitschuh.gradle.ktlint"
    const val SPOTLESS = "gradlePlugins.spotless"
}
