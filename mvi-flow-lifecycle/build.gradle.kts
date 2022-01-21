plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    id("com.jfrog.artifactory")
}

// lib info
val libVersion: String by project
val libGroup: String by project

publishing {
    publications {
        register("aar", MavenPublication::class) {
            version = libVersion
            groupId = libGroup
            artifactId = project.name
            artifact("$buildDir/outputs/aar/mvi-flow-lifecycle-$libVersion-release.aar")
        }
    }
}

artifactory {
    setContextUrl("https://artifactory.surfstudio.ru/artifactory")
    publish {
        repository {
            setRepoKey("libs-release-local")
            setUsername(System.getenv("surf_maven_username"))
            setPassword(System.getenv("surf_maven_password"))
        }
        defaults {
            publications("aar")
            setPublishArtifacts(true)
        }
    }
}

android {

    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31
        setProperty("archivesBaseName", "mvi-flow-lifecycle-$libVersion")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(project(":mvi-flow"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
}