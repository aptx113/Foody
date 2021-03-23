package dependencyLibs

import dependencyVersions.DependencyVersions.ACTIVITY_VERSION
import dependencyVersions.DependencyVersions.APPCOMPAT_VERSION
import dependencyVersions.DependencyVersions.COIL_VERSION
import dependencyVersions.DependencyVersions.CONSTRAINTLAYOUT_VERSION
import dependencyVersions.DependencyVersions.CORE_VERSION
import dependencyVersions.DependencyVersions.COROUTINES_VERSION
import dependencyVersions.DependencyVersions.FRAG_VERSION
import dependencyVersions.DependencyVersions.HILT_VERSION
import dependencyVersions.DependencyVersions.KOTLIN_VERSION
import dependencyVersions.DependencyVersions.LIFECYCLE_VERSION
import dependencyVersions.DependencyVersions.MATERIAL_VERSION
import dependencyVersions.DependencyVersions.MOSHI_VERSION
import dependencyVersions.DependencyVersions.NAV_VERSION
import dependencyVersions.DependencyVersions.OKHTTP3_VERSION
import dependencyVersions.DependencyVersions.PAGING_VERSION
import dependencyVersions.DependencyVersions.RECYCLERVIEW_VERSION
import dependencyVersions.DependencyVersions.RETROFIT2_VERSION
import dependencyVersions.DependencyVersions.ROOM_VERSION
import dependencyVersions.DependencyVersions.TIMBER_VERSION
import dependencyVersions.DependencyVersions.VIEWPAGER2_VERSION

/**
 * All the Project dependencies are declared here.
 * These can be used across the Project
 */
object AppCompat {
    const val APPCOMPAT = "androidx.appcompat:appcompat:${APPCOMPAT_VERSION}"
}

object Activity {
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${ACTIVITY_VERSION}"
}

object Coil {
    const val COIL = "io.coil-kt:coil:${COIL_VERSION}"
}

object ConstraintLayout {
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${CONSTRAINTLAYOUT_VERSION}"
}

object Core {
    const val CORE_KTX = "androidx.core:core-ktx:${CORE_VERSION}"
}

object Coroutines {
    const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${
        COROUTINES_VERSION
    }"
    const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${COROUTINES_VERSION}"
}

object Fragment {
    const val FRAG_KTX = "androidx.fragment:fragment-ktx:${FRAG_VERSION}"
}

object Hilt {
    const val ANDROID = "com.google.dagger:hilt-android:${HILT_VERSION}"
    const val COMPILER = "com.google.dagger:hilt-compiler:${HILT_VERSION}"
    const val HILT_GRADLE = "com.google.dagger:hilt-android-gradle-plugin:${HILT_VERSION}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}"
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KOTLIN_VERSION}"
}

object Lifecycle {
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LIFECYCLE_VERSION}"
    const val LIVEDATE = "androidx.lifecycle:lifecycle-livedata-ktx:${LIFECYCLE_VERSION}"
    const val VIEWMODEL_SAVEDSTATE =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${LIFECYCLE_VERSION}"
}

object Material {
    const val MATERIAL = "com.google.android.material:material:${MATERIAL_VERSION}"
}

object Moshi {
    const val KOTLIN = "com.squareup.moshi:moshi-kotlin:${MOSHI_VERSION}"
    const val CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${MOSHI_VERSION}"
}

object Navigation {
    const val FRAG = "androidx.navigation:navigation-fragment:${NAV_VERSION}"
    const val UI = "androidx.navigation:navigation-ui:${NAV_VERSION}"
    const val SAFE_ARGS_GRADLE =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${NAV_VERSION}"
}

object OkHttp3 {
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${OKHTTP3_VERSION}"
}

object Paging {
    const val PAGING3 = "androidx.paging:paging-runtime:${PAGING_VERSION}"
}

object RecyclerView {
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${RECYCLERVIEW_VERSION}"
}

object Retrofit2 {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${RETROFIT2_VERSION}"
    const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${RETROFIT2_VERSION}"
}

object Room {
    const val RUNTIME = "androidx.room:room-runtime:${ROOM_VERSION}"
    const val COMPILER = "androidx.room:room-compiler:${ROOM_VERSION}"
    const val KTX = "androidx.room:room-ktx:${ROOM_VERSION}"
}

object Timber {
    const val TIMBER = "com.jakewharton.timber:timber:${TIMBER_VERSION}"
}

object ViewPager2 {
    const val VIEWPAGER2 = "androidx.viewpager2:viewpager2:${VIEWPAGER2_VERSION}"
}

object Libraries {
    val libraries = arrayListOf<String>().apply {
        add(Activity.ACTIVITY_KTX)
        add(AppCompat.APPCOMPAT)
        add(Coil.COIL)
        add(ConstraintLayout.CONSTRAINT_LAYOUT)
        add(Core.CORE_KTX)
        add(Coroutines.ANDROID)
        add(Coroutines.CORE)
        add(Fragment.FRAG_KTX)
        add(Hilt.ANDROID)
        add(Kotlin.stdlib)
        add(Lifecycle.LIVEDATE)
        add(Lifecycle.VIEWMODEL)
        add(Lifecycle.VIEWMODEL_SAVEDSTATE)
        add(Material.MATERIAL)
        add(Moshi.CODEGEN)
        add(Moshi.KOTLIN)
        add(Navigation.FRAG)
        add(Navigation.UI)
        add(OkHttp3.LOGGING_INTERCEPTOR)
        add(Paging.PAGING3)
        add(RecyclerView.RECYCLERVIEW)
        add(Retrofit2.CONVERTER_MOSHI)
        add(Retrofit2.RETROFIT)
        add(Room.KTX)
        add(Room.RUNTIME)
        add(Timber.TIMBER)
        add(ViewPager2.VIEWPAGER2)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(Hilt.COMPILER)
        add(Room.COMPILER)
    }
}