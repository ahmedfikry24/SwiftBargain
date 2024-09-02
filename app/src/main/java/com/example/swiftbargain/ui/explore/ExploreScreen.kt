package com.example.swiftbargain.ui.explore

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.ControlItemVisibility
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.explore.composable.ExploreAppBar
import com.example.swiftbargain.ui.explore.composable.ExploreCategorySection
import com.example.swiftbargain.ui.explore.view_model.ExploreInteractions
import com.example.swiftbargain.ui.explore.view_model.ExploreUiState
import com.example.swiftbargain.ui.explore.view_model.ExploreViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
    ExploreContent(state = state, interactions = viewModel)
}

@Composable
private fun ExploreContent(
    state: ExploreUiState,
    interactions: ExploreInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        val scrollState = rememberLazyGridState()
        val scope = rememberCoroutineScope()
        ScrollToFirstItemFab(
            modifier = Modifier.fillMaxSize(),
            isFabVisible = scrollState.canScrollBackward,
            onClickFab = { scope.launch { scrollState.animateScrollToItem(0) } }
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                state = scrollState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ExploreAppBar(
                        isSearchVisible = state.isSearchVisible,
                        search = state.searchValue,
                        onClickSearchIcon = interactions::controlSearchVisibility,
                        onClickBack = interactions::controlSearchVisibility,
                        onClickMic = interactions::onClickMic,
                        onChangeSearch = interactions::onChangeSearch
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        isVisible = !state.isSearchVisible && state.manCategories.isNotEmpty()
                    ) {
                        ExploreCategorySection(
                            title = stringResource(R.string.man_fashion),
                            categoryList = state.manCategories,
                            onClickCategory = interactions::onClickCategory
                        )
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        isVisible = !state.isSearchVisible && state.womanCategories.isNotEmpty()
                    ) {
                        ExploreCategorySection(
                            title = stringResource(R.string.woman_fashion),
                            categoryList = state.womanCategories,
                            onClickCategory = interactions::onClickCategory
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space16),
                        isVisible = state.isSearchVisible && state.searchContentStatus == ContentStatus.LOADING
                    ) {
                        ContentLoading(isVisible = true)
                    }
                }
                items(state.searchProducts) { product ->
                    ControlItemVisibility(
                        modifier = Modifier.padding(MaterialTheme.spacing.space16),
                        isVisible = state.isSearchVisible && state.searchContentStatus == ContentStatus.VISIBLE
                    ) {
                        ProductItem(
                            item = product,
                            onClick = interactions::onClickProduct
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        isVisible = state.isSearchVisible && state.searchContentStatus == ContentStatus.FAILURE
                    ) {
                        ContentError(isVisible = true, onTryAgain = interactions::getSearchResult)
                    }
                }
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getCategories
    )
}
