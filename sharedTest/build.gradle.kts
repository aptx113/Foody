plugins {
    id("foody.android.library")
    id("foody.android.hilt")
    id("foody.android.library.compose")
}

android {
    namespace = "com.danteyu.studio.foody.sharedTest"
}

dependencies {
    implementation(project(":app"))

    implementation(libs.kotlinx.datetime)

    api(libs.junit4)
    api(libs.bundles.androidx.test)
    api(libs.kotlinx.coroutines.test)
    api(libs.truth)
    api(libs.turbine)

    api(libs.androidx.compose.ui.test)
    api(libs.hilt.android.testing)

    debugApi(libs.androidx.compose.ui.testManifest)
    debugApi(libs.androidx.fragment.testing)
}
