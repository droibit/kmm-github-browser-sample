package com.example.shared

import com.github.droibit.komol.Komol
import com.github.droibit.komol.PrintLogger

fun bootstrap(debuggable: Boolean) {
    if (debuggable) {
        Komol.initialize(PrintLogger())
    }
    Komol.d("Bootstrapped.")
}