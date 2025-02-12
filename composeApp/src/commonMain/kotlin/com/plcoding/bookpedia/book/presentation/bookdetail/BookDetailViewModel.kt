package com.plcoding.bookpedia.book.presentation.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandler: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val bookWorkId = savedStateHandler.toRoute<Route.BookDetail>().id

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnFavoriteClick -> {
                _state.update {
                    it.copy(
                        isFavorite = !it.isFavorite
                    )
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(book = action.book)
                }
            }

            else -> Unit
        }
    }

    fun fetchBookDescription() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            bookRepository.fetchBookDescription(bookWorkId = bookWorkId)
                .onSuccess { bookDescription ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            book = it.book?.copy(
                                description = bookDescription
                            )
                        )
                    }
                }
        }
    }
}