import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.Plugins.Kotlin.gradle)
        classpath(Deps.Plugins.Kotlin.serialization)
        classpath(Deps.Plugins.android)
        classpath(Deps.Plugins.sqlDelight)
        classpath(Deps.Plugins.daggerHilt)
    }
}

plugins {
    id("com.diffplug.spotless") version Deps.Version.spotless
}

subprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.repsy.io/mvn/droibit/public")
        maven(url = "https://repo.repsy.io/mvn/chrynan/public")
    }

    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("**/generated/**/*.kt")
            ktlint(Deps.Version.ktlint)
                .userData(
                    mapOf(
                        "android" to "true",
                        "experimental" to "true"
                    )
                )

        }
    }

    plugins.whenPluginAdded {
        if (this is AppPlugin || this is LibraryPlugin || this is DynamicFeaturePlugin) {
            project.extensions.getByType<BaseExtension>().apply {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xinline-classes",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlin.ExperimentalMultiplatform"
            )
        }
    }
}