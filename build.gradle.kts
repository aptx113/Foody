/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import ext.applyDefault

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(dependencyLibs.Hilt.HILT_GRADLE)
        classpath(dependencyLibs.Navigation.SAFE_ARGS_GRADLE)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id(Plugins.DETEKT) version PluginVersions.DETEKT_VERSION
    id(Plugins.DOKKA) version PluginVersions.DOKKA_VERSION
    id(Plugins.GIT_HOOKS)
    id(Plugins.GRADLE_VERSIONS_PLUGIN) version PluginVersions.GRADLE_VER_PLUGIN_VERSION
    id(Plugins.KTLINT) version PluginVersions.KTLINT_VERSION
}

allprojects {
    repositories.applyDefault()

    apply {
        plugin(Plugins.DOKKA)
        plugin(Plugins.DETEKT)
        plugin(Plugins.KTLINT)
        plugin(Plugins.SPOTLESS)
    }

    detekt {
        config = rootProject.files("$rootDir/.detekt/config.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt/report.html")
            }
        }
    }

    ktlint {
        debug.set(false)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(false)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean =
    listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
        .any { it.matches(version) }
