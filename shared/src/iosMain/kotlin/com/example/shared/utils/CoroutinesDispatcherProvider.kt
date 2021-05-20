package com.example.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main

actual class CoroutinesDispatcherProvider(
    actual val main: CoroutineDispatcher,
    actual val computation: CoroutineDispatcher,
    actual val io: CoroutineDispatcher
) {
    constructor() : this(Main, Default, Default)

    companion object {
        internal val sharedInstance = CoroutinesDispatcherProvider()
    }
}
