package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor


// Only necessary for KMP projects only

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
