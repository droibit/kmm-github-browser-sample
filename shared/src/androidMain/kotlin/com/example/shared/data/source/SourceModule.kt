package com.example.shared.data.source

import android.content.Context
import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.ListOfStringsAdapter
import com.example.shared.data.source.local.db.RepoSearchResult
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(
            driver = AndroidSqliteDriver(
                schema = AppDatabase.Schema,
                context = context,
                name = "github.db"
            ),
            repoSearchResultAdapter = RepoSearchResult.Adapter(
                repoIdsAdapter = ListOfStringsAdapter
            )
        )
    }

    @Singleton
    @Provides
    fun provideHttpClient(@Named("debuggable") debuggable: Boolean): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                if (debuggable) {
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    addInterceptor(loggingInterceptor)
                }
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}