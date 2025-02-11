package com.plcoding.bookpedia.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.bookdetail.BookDetailAction
import com.plcoding.bookpedia.book.presentation.bookdetail.BookDetailScreenRoot
import com.plcoding.bookpedia.book.presentation.bookdetail.BookDetailViewModel
import com.plcoding.bookpedia.book.presentation.booklist.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.booklist.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.BookGraph
        ) {
            navigation<Route.BookGraph>(startDestination = Route.BookList) {

                composable<Route.BookList> { entry ->
                    val viewModel: BookListViewModel = koinViewModel()
                    val selectedBookViewModel: SelectedBookViewModel = entry.sharedKoinViewModel(navController)

                    LaunchedEffect(Unit) {
                        println("Cleaning book!")
                        selectedBookViewModel.onSelectBook(null)
                    }

                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClicked = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(route = Route.BookDetail(book.id))
                        }
                    )
                }

                composable<Route.BookDetail> { entry ->
                    val viewModel: BookDetailViewModel = koinViewModel()
                    val selectedBookViewModel: SelectedBookViewModel = entry.sharedKoinViewModel(navController)

                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.run {
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(book = this))
                        }
                    }

                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraph = destination.parent?.route ?: return koinViewModel<T>()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraph)
    }

    return koinViewModel(viewModelStoreOwner = parentEntry)
}