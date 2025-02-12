package com.plcoding.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.FavoriteBookDao
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.mappers.toBookEntity
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    val remoteBookDataSource: RemoteBookDataSource,
    val bookDao: FavoriteBookDao
) : BookRepository {

    override suspend fun searchBook(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query = query)
            .map { dto ->
                dto.results.map {
                    it.toBook()
                }
            }
    }

    override suspend fun fetchBookDescription(bookWorkId: String): Result<String?, DataError> {
        val localResult = bookDao.getFavoriteBook(bookWorkId)
        if (localResult != null) return Result.Success(localResult.description)

        return remoteBookDataSource
            .fetchBookDescription(bookWorkId = bookWorkId)
            .map { it.description }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoritesBook()
            .map { bookEntities ->
                bookEntities.map { bookEntity ->
                    bookEntity.toBook()
                }
            }
    }

    override fun isBookFavorite(bookWorkId: String): Flow<Boolean> {
        return bookDao.getFavoritesBook()
            .map { bookEntities ->
                bookEntities.any { bookEntity ->
                    bookEntity.id == bookWorkId
                }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            bookDao.upsert(bookEntity = book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(error = DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        bookDao.deleteFavoriteBook(id)
    }
}