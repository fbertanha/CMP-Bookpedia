package com.plcoding.bookpedia.book.presentation.booklist


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.*
import com.plcoding.bookpedia.book.data.mockBooks
import com.plcoding.bookpedia.book.data.mockFavorites
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.presentation.booklist.components.BookList
import com.plcoding.bookpedia.book.presentation.booklist.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClicked: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(state = state, onAction = { action ->
        when (action) {
            is BookListAction.OnBookClick -> {
                onBookClicked(action.book)
            }

            else -> viewModel.onAction(action)
        }
    })
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val keyboardCOntroller = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }

    val searchResultsState = rememberLazyListState()
    val favoritesState = rememberLazyListState()

    //TODO test this later
    LaunchedEffect(state.searchResults) {
        searchResultsState.animateScrollToItem(0)
    }

    LaunchedEffect(state.favoriteBooks) {
        favoritesState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = { searchText -> onAction(BookListAction.OnSearchQueryChange(searchText)) },
            onImeSearch = {
                keyboardCOntroller?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    indicator = { tabIndicator ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabIndicator[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIdex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            state.isLoading -> {
                                CircularProgressIndicator()
                            }

                            state.errorMessage != null -> {
                                Text(
                                    text = state.errorMessage.asString(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            pageIdex == 0 && state.searchResults.isEmpty() && state.searchQuery.length <= 2 -> {
                                Text(
                                    text = stringResource(Res.string.start_search),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }

                            pageIdex == 0 && state.searchResults.isEmpty() -> {
                                Text(
                                    text = stringResource(Res.string.no_search_result),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }

                            pageIdex == 1 && state.favoriteBooks.isEmpty() -> {
                                Text(
                                    text = stringResource(Res.string.no_favorites),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }

                            else -> {
                                BookList(
                                    books = if (pageIdex == 0) state.searchResults else state.favoriteBooks,
                                    onBookClick = { book -> onAction(BookListAction.OnBookClick(book)) },
                                    scrollState = if (pageIdex == 0) searchResultsState else favoritesState
                                )
                            }
                        }
                    }
                }
            }

        }
    }

}

@Preview
@Composable
fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchQuery = "Java",
            selectedTabIndex = 1,
            searchResults = mockBooks,
            favoriteBooks = mockFavorites,
            isLoading = false
        ),
        onAction = {})
}