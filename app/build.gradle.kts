import com.danteyu.studio.foody.config.DefaultConfigs
import dependencyLibs.AndroidTestDependencies.androidTestLibraries
import dependencyLibs.Libraries.libraries
import com.danteyu.studio.foody.FoodyBuildType
import dependencyLibs.TestDependencies.testLibraries
import ext.addAndroidTestDependencies
import ext.addDependencies
import ext.addTestDependencies

plugins {
    id("foody.android.application")
    kotlin("kapt")
    id("foody.android.application.compose")
    id("foody.android.application.jacoco")
    id("foody.android.application.flavors")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
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

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")

            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
        }

        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.APPLICATION_ID_SUFFIX
            versionNameSuffix = BuildTypeDebug.VERSION_NAME_SUFFIX
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
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
    namespace = DefaultConfigs.NAME_SPACE
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    addDependencies(libraries)
    addTestDependencies(testLibraries)
    addAndroidTestDependencies(androidTestLibraries)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
}
