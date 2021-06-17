package com.example.shared.data.source.remote.api

import io.ktor.client.statement.HttpResponse

class ApiError(
    val response: HttpResponse,
    cause: Throwable? = null
) : Exception(cause) {

    override fun toString(): String {
        if (cause == null) {
            return response.status.toString()
        }
        return super.toString()
    }
}