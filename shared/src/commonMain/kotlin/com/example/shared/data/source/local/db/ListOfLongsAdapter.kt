package com.example.shared.data.source.local.db

import com.squareup.sqldelight.ColumnAdapter

internal object ListOfLongsAdapter : ColumnAdapter<List<Long>, String> {
    override fun decode(databaseValue: String) = databaseValue.split(",").map { it.toLong() }

    override fun encode(value: List<Long>) = value.joinToString(separator = ",")
}