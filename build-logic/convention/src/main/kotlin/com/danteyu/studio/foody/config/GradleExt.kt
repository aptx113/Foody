package com.danteyu.studio.foody.config

import com.danteyu.studio.foody.config.LibsConst.ANDROID_TEST_IMPLEMENTATION
import com.danteyu.studio.foody.config.LibsConst.IMPLEMENTATION
import com.danteyu.studio.foody.config.LibsConst.KSP
import com.danteyu.studio.foody.config.LibsConst.TEST_IMPLEMENTATION
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implement(dependencyNotation: Any) =
    add(IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.implementAndroidTest(dependencyNotation: Any) = add(
    ANDROID_TEST_IMPLEMENTATION, dependencyNotation
)

fun DependencyHandlerScope.implementTest(dependencyNotation: Any) = add(
    TEST_IMPLEMENTATION, dependencyNotation
)

fun DependencyHandlerScope.addKsp(dependencyNotation: Any) = add(
    KSP, dependencyNotation
)
