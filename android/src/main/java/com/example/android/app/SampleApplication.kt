package com.example.android.app

import android.app.Application
import com.github.droibit.komol.Komol
import com.github.droibit.komol.timber.TimberLogger

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Komol.initialize(TimberLogger())
        }
    }
}