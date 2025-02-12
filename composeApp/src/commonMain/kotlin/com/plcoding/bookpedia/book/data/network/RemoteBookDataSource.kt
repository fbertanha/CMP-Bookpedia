package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.network.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.network.dto.SearchResponseDto
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result


interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchBookDescription(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}