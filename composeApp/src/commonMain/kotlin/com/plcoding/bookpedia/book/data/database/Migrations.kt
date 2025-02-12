package com.plcoding.bookpedia.book.data.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SQLiteConnection) {
        database.execSQL(
            "DELETE FROM BookEntityy"
        )
    }
}