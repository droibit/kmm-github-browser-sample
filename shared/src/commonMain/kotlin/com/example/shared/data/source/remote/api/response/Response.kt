package com.example.shared.data.source.remote.api.response

import io.ktor.client.call.receive
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.isSuccess

class Response<T>(
    val body: T?,
    val raw: HttpResponse,
) {
    val code: Int get() = raw.status.value
    val isSuccessful: Boolean get() = raw.status.isSuccess()
    val headers: Headers get() = raw.headers

    override fun toString(): String {
        return raw.toString()
    }

    companion object {
        suspend inline fun <reified T> create(rawResponse: HttpResponse): Response<T> {
            // MEMO: The detailed control is omitted for the sample.
            if (!rawResponse.status.isSuccess()) {
                return Response(null, rawResponse)
            }
            return Response(
                body = rawResponse.receive(),
                raw = rawResponse
            )
        }
    }
}