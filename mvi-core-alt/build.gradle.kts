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
}

dependencies {
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
}