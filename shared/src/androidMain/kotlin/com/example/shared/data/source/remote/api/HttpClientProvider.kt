package com.example.shared.data.source.remote.api

import com.chrynan.inject.Inject
import com.chrynan.inject.Named
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

class HttpClientProvider @Inject constructor(
    @Named("debuggable") private val debuggable: Boolean
) {

    private val httpClient: HttpClient by lazy {
        HttpClient(OkHttp) {
            engine {
                if (debuggable) {
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = BODY
                    }
                    addInterceptor(loggingInterceptor)
                }
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    fun get(): HttpClient = httpClient
}