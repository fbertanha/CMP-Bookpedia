package com.plcoding.bookpedia.book.presentation.booklist

import com.plcoding.bookpedia.book.data.mockBooks
import com.plcoding.bookpedia.book.data.mockFavorites
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = mockBooks,
    val favoriteBooks: List<Book> = mockFavorites,
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)