package com.example.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main

internal class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher
) {
    constructor() : this(Main, Default)

    companion object {
        internal val sharedInstance = CoroutinesDispatcherProvider()
    }
}
