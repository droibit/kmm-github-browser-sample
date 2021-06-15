package com.example.android.app

import android.app.Application
import com.github.droibit.komol.Komol
import com.github.droibit.komol.timber.TimberLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class SampleApplication : Application() {
    @Inject
    fun bootstrap(@Named("debuggable") debuggable: Boolean) {
        if (debuggable) {
            Komol.initialize(TimberLogger())
        }
        Komol.d("Bootstrapped!")
    }
}