package com.example.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun interface Closeable {
    fun close()
}

/**
 * ref. [CFlow](https://github.com/Kotlin/kmm-production-sample/blob/master/shared/src/iosMain/kotlin/com/github/jetbrains/rssreader/core/CFlow.kt)
 */
class CFlow<T> internal constructor(
    private val origin: Flow<T>,
    private val dispatcher: CoroutineDispatcher
) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(dispatcher + job))

        return Closeable { job.cancel() }
    }

    fun watch(block: (T?, Throwable?) -> Unit): Closeable {
        val job = Job()

        onEach {
            block(it, null)
        }.catch { e ->
            block(null, e)
        }.launchIn(CoroutineScope(dispatcher + job))

        return Closeable { job.cancel() }
    }
}

internal fun <T> Flow<T>.wrap(dispatcher: CoroutineDispatcher = Dispatchers.Main): CFlow<T> =
    CFlow(this, dispatcher)