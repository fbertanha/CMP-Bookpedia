package com.plcoding.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DATABASE_NAME = "book.db"
    }
}