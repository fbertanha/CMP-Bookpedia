package com.plcoding.bookpedia.book.presentation.bookdetail

import com.plcoding.bookpedia.book.domain.model.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnFavoriteClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}