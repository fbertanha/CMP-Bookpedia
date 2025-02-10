package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import com.plcoding.bookpedia.book.presentation.booklist.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.booklist.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel: BookListViewModel = koinViewModel()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClicked = {}
    )
}