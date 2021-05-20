plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

version = "1.0.0"

android {
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        resourceConfigurations += listOf("en", "ja")
    }

    buildFeatures {
        buildConfig = false
    }

    buildTypes {
        getByName("release") {
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    // Workaround for https://youtrack.jetbrains.com/issue/KT-43944
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    android()
    ios()

    // TODO: Automatically enable "sqlite3".
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Shared module for the app."
        homepage = "https://github.com/droibit/kotlin-multiplatform-mobile-template"
        authors = "Shinya Kumagai"
        license = "Apache License, Version 2.0"
        frameworkName = "Shared"

        ios.deploymentTarget = "14.0"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Coroutines.core)
                implementation(Deps.Stately.common)
                implementation(Deps.Serialization.core)
                implementation(Deps.Serialization.json)
                implementation(Deps.Ktor.Client.core)
                implementation(Deps.Ktor.Client.serialization)
                implementation(Deps.SQLDelight.runtime)
                implementation(Deps.SQLDelight.coroutines)
                implementation(Deps.inject)
                implementation(Deps.Komol.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Deps.Test.Kotlin.common)
                implementation(Deps.Test.Kotlin.annotationsCommon)
                implementation(Deps.Test.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.Ktor.Client.okhttp)
                implementation(Deps.OkHttp.client)
                implementation(Deps.OkHttp.loggingInterceptor)
                implementation(Deps.SQLDelight.Driver.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Deps.Test.junit)
                implementation(Deps.Test.Kotlin.junit)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.Ktor.Client.ios)
                implementation(Deps.SQLDelight.Driver.native)
            }
        }
        val iosTest by getting
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.shared.data.source.local.db"
        schemaOutputDirectory = File(projectDir, "schemas")
    }

    linkSqlite = true
}
