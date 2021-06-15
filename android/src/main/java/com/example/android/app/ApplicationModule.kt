package com.example.android.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Named("debuggable")
    @Provides
    fun provideDebuggable(): Boolean = BuildConfig.DEBUG
}