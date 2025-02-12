package com.plcoding.bookpedia.book.domain.repository

import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun searchBook(query: String): Result<List<Book>, DataError.Remote>

    suspend fun fetchBookDescription(bookWorkId: String): Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>

    fun isBookFavorite(bookWorkId: String): Flow<Boolean>

    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>

    suspend fun deleteFromFavorites(id: String)
}