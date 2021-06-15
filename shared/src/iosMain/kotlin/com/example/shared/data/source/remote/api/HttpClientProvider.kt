package com.example.shared.data.source.remote.api

import co.touchlab.stately.freeze
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios

object HttpClientProvider {
    private val httpClient: HttpClient by lazy {
        HttpClient(Ios) {
            engine {
                configureSession {
                    timeoutIntervalForRequest = 30
                }
            }
        }.freeze()
    }

    fun get(): HttpClient = httpClient
}