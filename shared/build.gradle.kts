import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "Shared"
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

val packForXcode by tasks.creating(Sync::class) {
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    val targetDir = File(buildDir, "xcode-frameworks")

    group = "build"
    dependsOn(framework.linkTask)
    inputs.property("mode", mode)

    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)