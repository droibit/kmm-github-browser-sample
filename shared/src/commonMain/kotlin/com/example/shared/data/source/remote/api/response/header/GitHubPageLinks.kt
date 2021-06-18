package com.example.shared.data.source.remote.api.response.header

import io.ktor.http.Headers
import io.ktor.http.Url
import kotlin.LazyThreadSafetyMode.NONE

data class GitHubPageLinks(
    val value: Map<String, String>
) {
    val nextPage: Int? by lazy(NONE) {
        value["next"]?.run {
            Url(this).parameters["page"]?.toIntOrNull()
        }
    }

    companion object {
        private const val KEY_LINK = "link"
        private val LINK_REGEX = Regex("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        operator fun invoke(headers: Headers): GitHubPageLinks? {
            val linkHeader = headers[KEY_LINK] ?: return null
            val links = LINK_REGEX.findAll(linkHeader).mapNotNull {
                val url = it.groups[1]?.value ?: return@mapNotNull null
                val rel = it.groups[2]?.value ?: return@mapNotNull null
                (rel to url)
            }.toMap()

            return GitHubPageLinks(links)
        }
    }
}