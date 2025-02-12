package com.plcoding.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Upsert
    suspend fun upsert(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoritesBook(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :bookWorkId")
    suspend fun getFavoriteBook(bookWorkId: String): BookEntity?

    @Query("DELETE FROM BookEntity WHERE id = :bookWorkId")
    suspend fun deleteFavoriteBook(bookWorkId: String)

}