package com.danteyu.studio.foody

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

enum class FlavorDimension {
    ContentType
}

enum class AppFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    Demo(FlavorDimension.ContentType, applicationIdSuffix = ".demo"),
    Qat(FlavorDimension.ContentType, applicationIdSuffix = ".qat"),
    Prod(FlavorDimension.ContentType)
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.ContentType.name
        productFlavors {
            AppFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
