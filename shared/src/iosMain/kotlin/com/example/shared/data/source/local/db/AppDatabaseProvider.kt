package com.example.shared.data.source.local.db

import co.touchlab.stately.freeze
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

object AppDatabaseProvider {
    private val database: AppDatabase by lazy {
        AppDatabase(
            driver = NativeSqliteDriver(AppDatabase.Schema, "github.db"),
            repoSearchResultAdapter = RepoSearchResult.Adapter(
                repoIdsAdapter = ListOfStringsAdapter
            )
        ).freeze()
    }

    fun get(): AppDatabase = database
}