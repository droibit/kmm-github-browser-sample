package com.example.shared.data.source.local.db

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

object AppDatabaseProvider {
    fun get(): AppDatabase {
        return AppDatabase(
            driver = NativeSqliteDriver(AppDatabase.Schema, "github.db"),
            repoSearchResultAdapter = RepoSearchResult.Adapter(
                repoIdsAdapter = ListOfLongsAdapter
            )
        )
    }
}