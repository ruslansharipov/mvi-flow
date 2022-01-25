plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    id("com.jfrog.artifactory")
}

val libraryConfig: () -> Unit by project.extra
val androidConfig: Any.() -> Unit by project.extra

libraryConfig()

android {
    androidConfig()

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    api(project(":mvi-core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
}