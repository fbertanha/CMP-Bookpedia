package com.plcoding.bookpedia.book.presentation.booklist

import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)