package com.plcoding.bookpedia.book.presentation.booklist

import com.plcoding.bookpedia.book.domain.model.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction
}