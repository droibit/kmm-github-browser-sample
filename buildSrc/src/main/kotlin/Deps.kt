object Deps {
    object Plugins {
        const val android = "com.android.tools.build:gradle:7.0.0-alpha15"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Dagger.version}"
        const val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Androidx.Navigation.version}"

        object Kotlin {
            internal const val version = "1.5.0"
            const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        }
    }

    object Coroutines {
        // Strictly requires `native-mt` version.
        private const val version = "1.4.3-native-mt!!"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${version}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Stately {
        const val common = "co.touchlab:stately-common:1.1.4"
    }

    object Androidx {
        const val core = "androidx.core:core-ktx:1.6.0-alpha02"
        const val activity = "androidx.activity:activity-ktx:1.3.0-alpha07"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.3"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0-rc01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0"

        object Navigation {
            internal const val version = "2.3.5"
            const val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.4.0-alpha01"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    object Dagger {
        internal const val version = "2.35.1"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Komol {
        private const val version = "0.0.3"
        const val core = "com.github.droibit.komol:komol-core:$version"
        const val timber = "com.github.droibit.komol:komol-timber:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"

        object Kotlin {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Plugins.Kotlin.version}"
            const val annotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Plugins.Kotlin.version}"
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Plugins.Kotlin.version}"
        }
    }

    const val materialDesign = "com.google.android.material:material:1.4.0-alpha01"
    const val inject = "com.chrynan.inject:inject:0.8.0"

    object Version {
        const val ktlint = "0.40.0"
        const val spotless = "5.10.1"
    }
}