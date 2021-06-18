package com.example.shared.data.source.remote.api

import co.touchlab.stately.freeze
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object HttpClientProvider {
    private val httpClient: HttpClient by lazy {
        HttpClient(Ios) {
            engine {
                configureSession {
                    timeoutIntervalForRequest = 30.0
                }
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
            // TODO: Logging
        }.freeze()
    }

    fun get(): HttpClient = httpClient
}