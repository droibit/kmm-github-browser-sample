object Deps {
    object Plugins {
        const val android = "com.android.tools.build:gradle:7.0.3"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${SQLDelight.version}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Dagger.version}"
        const val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Androidx.Navigation.version}"

        object Kotlin {
            internal const val version = "1.6.0"
            const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
            const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"
        }
    }

    object Coroutines {
        // Strictly requires `native-mt` version.
        private const val version = "1.6.0-RC"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${version}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Stately {
        const val common = "co.touchlab:stately-common:1.2.0"
    }

    object Androidx {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val activity = "androidx.activity:activity-ktx:1.4.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

        object Navigation {
            internal const val version = "2.4.0-beta02"
            const val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    object Serialization {
        private const val version = "1.3.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"
        const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
    }

    object Ktor {
        private const val version = "1.6.6"

        object Client {
            const val core = "io.ktor:ktor-client-core:$version"
            const val okhttp = "io.ktor:ktor-client-okhttp:$version"
            const val ios = "io.ktor:ktor-client-ios:$version"
            const val serialization = "io.ktor:ktor-client-serialization:$version"
        }
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val client = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object SQLDelight {
        internal const val version = "1.5.0"
        const val runtime = "com.squareup.sqldelight:runtime:$version"
        const val coroutines = "com.squareup.sqldelight:coroutines-extensions:$version"

        object Driver {
            const val android = "com.squareup.sqldelight:android-driver:$version"
            const val native = "com.squareup.sqldelight:native-driver:$version"
        }
    }

    object Dagger {
        const val version = "2.40.1"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Komol {
        private const val version = "0.1.0"
        const val core = "com.github.droibit.komol:komol-core:$version"
        const val timber = "com.github.droibit.komol:komol-timber:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val turbine = "app.cash.turbine:turbine:0.4.1"

        object Kotlin {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Plugins.Kotlin.version}"
            const val annotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Plugins.Kotlin.version}"
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Plugins.Kotlin.version}"
        }
    }

    const val materialDesign = "com.google.android.material:material:1.4.0-rc01"
    const val coil = "io.coil-kt:coil:1.2.2"
    const val inject = "com.chrynan.inject:inject:1.0.0"

    object Version {
        const val ktlint = "0.40.0"
        const val spotless = "5.10.1"
    }
}