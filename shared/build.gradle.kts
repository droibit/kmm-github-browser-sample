plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id("dagger.hilt.android.plugin")
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

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    android()
    ios() {
        binaries {
            framework {
                baseName = "Shared"
                linkerOpts("-lsqlite3")
                export(Deps.Komol.core)
                // transitiveExport = true
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Coroutines.core)
                implementation(Deps.Stately.common)
                implementation(Deps.Serialization.core)
                implementation(Deps.Serialization.json)
                api(Deps.Ktor.Client.core)
                implementation(Deps.Ktor.Client.serialization)
                implementation(Deps.SQLDelight.runtime)
                implementation(Deps.SQLDelight.coroutines)
                api(Deps.inject)
                api(Deps.Komol.core)
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

                implementation(Deps.Dagger.hilt)
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

dependencies {
    "kapt"(Deps.Dagger.compiler)
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.shared.data.source.local.db"
        schemaOutputDirectory = File(projectDir, "schemas")
    }
    linkSqlite = true
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("dagger.experimentalDaggerErrorMessages", "enabled")
    }
}