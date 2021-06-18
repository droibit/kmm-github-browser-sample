package com.example.shared.data.source.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

object HttpClientProvider {
    fun get(): HttpClient {
        return HttpClient(Ios) {
            engine {
                configureSession {
                    timeoutIntervalForRequest = 30.0
                }
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        isLenient
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                        // Workaround for https://github.com/Kotlin/kotlinx.serialization/issues/1450
                        useAlternativeNames = false
                    }
                )
            }
            // TODO: Logging
        }
    }
}