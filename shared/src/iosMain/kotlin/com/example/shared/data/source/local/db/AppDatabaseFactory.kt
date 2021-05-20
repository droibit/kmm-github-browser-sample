package com.example.shared.data.source.local.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

object AppDatabaseFactory {
    fun make(): AppDatabase {
        return AppDatabase(
            driver = NativeSqliteDriver(AppDatabase.Schema, "github.db"),
            repoSearchResultAdapter = RepoSearchResult.Adapter(
                repoIdsAdapter = ListOfStringsAdapter
            )
        )
    }
}