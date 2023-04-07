import com.danteyu.studio.foody.FoodyBuildType
import com.danteyu.studio.foody.config.DefaultConfigs
import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("foody.android.application")
    id("foody.android.application.compose")
    id("foody.android.application.jacoco")
    id("foody.android.application.flavors")
    id("foody.android.hilt")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    alias(libs.plugins.protobuf)
}

android {

    defaultConfig {
        applicationId = DefaultConfigs.APPLICATION_ID

        versionCode = DefaultConfigs.VERSION_CODE
        versionName = DefaultConfigs.VERSION_NAME

        testInstrumentationRunner = DefaultConfigs.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = FoodyBuildType.Debug.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = FoodyBuildType.Release.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    kotlin {
        jvmToolchain(11)
    }
    namespace = DefaultConfigs.NAME_SPACE
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }

                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    testImplementation(project(":sharedTest"))
    androidTestImplementation(project(":sharedTest"))

    implementation(libs.bundles.androidx.activity)
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.constraintlayout)

    implementation(libs.bundles.androidx.core)

    implementation(libs.bundles.androidx.dataStore)

    implementation(libs.androidx.fragment.fragment)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.bundles.androidx.navigation)

    implementation(libs.androidx.paging)

    implementation(libs.androidx.recyclerview)

    implementation(libs.androidx.startup)

    implementation(libs.bundles.coil)


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    kapt(libs.hilt.ext.compiler)
    androidTestImplementation(libs.androidx.work.testing)

    implementation(libs.jsoup)

    implementation(libs.bundles.kotlinx)

    implementation(libs.material)

    implementation(libs.okhttp.logging)

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.shimmer)

    implementation(libs.timber)

    implementation(libs.xLog)
}
