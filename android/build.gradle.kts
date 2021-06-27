plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.android.app.github"
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        resourceConfigurations + listOf("en", "ja")
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    signingConfigs {
        getByName("debug") {
//            storeFile = rootProject.file("keystore/debug.keystore")
        }
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(Deps.Coroutines.core)
    implementation(Deps.Coroutines.android)

    implementation(Deps.Androidx.core)
    implementation(Deps.Androidx.activity)
    implementation(Deps.Androidx.appCompat)
    implementation(Deps.Androidx.fragment)

    implementation(Deps.materialDesign)

    implementation(Deps.Dagger.hilt)
    "kapt"(Deps.Dagger.compiler)

    implementation(Deps.Komol.core)
    implementation(Deps.Komol.timber)

    implementation(Deps.Ktor.Client.core)
}