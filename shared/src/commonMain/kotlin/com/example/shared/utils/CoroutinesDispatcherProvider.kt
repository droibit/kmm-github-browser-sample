package com.example.shared.utils

import kotlinx.coroutines.CoroutineDispatcher

expect class CoroutinesDispatcherProvider {
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val io: CoroutineDispatcher
}
