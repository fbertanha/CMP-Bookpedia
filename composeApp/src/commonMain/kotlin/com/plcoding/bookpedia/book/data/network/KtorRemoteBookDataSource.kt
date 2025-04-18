package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.network.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.network.dto.SearchResponseDto
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.*
import io.ktor.client.request.*

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(urlString = "$BASE_URL/search.json") {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

    override suspend fun fetchBookDescription(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(urlString = "$BASE_URL/works/$bookWorkId.json") {

            }
        }
    }
}