package com.example.shared.data.source.remote.api.response.header

import io.ktor.http.Headers

internal fun Headers.getInt(key: String): Int? = this[key]?.run { toIntOrNull() }
internal fun Headers.getLong(key: String): Long? = this[key]?.run { toLongOrNull() }