package com.example.shared.data.source.remote.api.response.header

import io.ktor.http.Headers

/**
 * ref. https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting
 */
data class GitHubApiRateLimit(
    val limit: Int,
    val remaining: Int,
    val used: Int,
    val reset: Long,
) {
    companion object {
        private const val KEY_LIMIT = "x-ratelimit-limit"
        private const val KEY_REMAINING = "x-ratelimit-remaining"
        private const val KEY_USED = "x-ratelimit-used"
        private const val KEY_RESET = "x-ratelimit-reset"

        operator fun invoke(headers: Headers): GitHubApiRateLimit? {
            val limit = headers.getInt(KEY_LIMIT) ?: return null
            val remaining = headers.getInt(KEY_REMAINING) ?: return null
            val used = headers.getInt(KEY_USED) ?: return null
            val reset = headers.getLong(KEY_RESET) ?: return null
            return GitHubApiRateLimit(limit, remaining, used, reset)
        }
    }
}

private fun Headers.getInt(key: String): Int? = this[key]?.run { toIntOrNull() }
private fun Headers.getLong(key: String): Long? = this[key]?.run { toLongOrNull() }