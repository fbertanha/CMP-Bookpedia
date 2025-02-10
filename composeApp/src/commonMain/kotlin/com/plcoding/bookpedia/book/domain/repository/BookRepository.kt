package com.plcoding.bookpedia.book.domain.repository

import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface BookRepository {

    suspend fun searchBook(query: String): Result<List<Book>, DataError.Remote>

}