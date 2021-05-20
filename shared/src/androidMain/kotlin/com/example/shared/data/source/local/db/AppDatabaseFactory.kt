package com.example.shared.data.source.local.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import javax.inject.Inject

class AppDatabaseFactory @Inject constructor() {
    fun create(context: Context): AppDatabase {
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
}