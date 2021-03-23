package ext

import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * util functions for adding the different type dependencies from build.gradle file
 */
fun DependencyHandler.addKapt(dependencies: List<String>) {
    dependencies.forEach { add("kapt", it) }
}

fun DependencyHandler.addDependencies(dependencies: List<String>) {
    dependencies.forEach { add("implementation", it) }
}

fun DependencyHandler.addAndroidTestDependencies(dependencies: List<String>) {
    dependencies.forEach { add("androidTestImplementation", it) }
}

fun DependencyHandler.addTestDependencies(dependencies: List<String>) {
    dependencies.forEach { add("testImplementation", it) }
}
