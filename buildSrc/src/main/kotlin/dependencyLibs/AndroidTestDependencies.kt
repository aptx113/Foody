package dependencyLibs

import dependencyVersions.AndroidTestDependencyVersions.ESPRESSO_VERSION
import dependencyVersions.AndroidTestDependencyVersions.TEST_EXT_VERSION
import dependencyVersions.DependencyVersions.NAV_VERSION

/**
 * All the Project Android Test dependencies are declared here.
 * These can be used across the Project
 */
object AndroidTestDependencies {
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${ESPRESSO_VERSION}"
    const val NAV_TESTING = "androidx.navigation:navigation-testing:${NAV_VERSION}"
    const val TEST_EXT_JUNIT = "androidx.test.ext:junit:${TEST_EXT_VERSION}"

    val androidTestLibraries = arrayListOf<String>().apply {
        add(ESPRESSO_CORE)
        add(NAV_TESTING)
        add(TEST_EXT_JUNIT)
    }
}


