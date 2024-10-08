package com.example.swiftbargain.ui.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.composable.Banner
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.ControlItemVisibility
import com.example.swiftbargain.ui.composable.LifeCycleTracker
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.sale.composable.SaleAppBar
import com.example.swiftbargain.ui.sale.view_model.SaleEvents
import com.example.swiftbargain.ui.sale.view_model.SaleInteractions
import com.example.swiftbargain.ui.sale.view_model.SaleUiState
import com.example.swiftbargain.ui.sale.view_model.SaleViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import kotlinx.coroutines.launch

@Composable
fun SaleScreen(
    navController: NavController,
    viewModel: SaleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDataArrive by rememberSaveable { mutableStateOf(false) }
    LifeCycleTracker { event ->
        if (event == Lifecycle.Event.ON_CREATE && !isDataArrive) {
            viewModel.getProducts()
            viewModel.observeSearch()
            isDataArrive = true
        }
    }
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            SaleEvents.GoBack -> navController.popBackStack()
            is SaleEvents.GoToProduct -> navController.navigate(ProductDetails(event.id))
        }
    }
    SaleContent(state = state, interactions = viewModel)
}


@Composable
private fun SaleContent(
    state: SaleUiState,
    interactions: SaleInteractions
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
                contentPadding = PaddingValues(MaterialTheme.spacing.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SaleAppBar(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.space8),
                        title = state.title,
                        searchText = state.search,
                        isSearchError = false,
                        onClickBack = interactions::onClickBack,
                        onClickSearch = interactions::controlSearchVisibility,
                        onChangeSearch = interactions::onChangeSearch,
                        isSearchVisible = state.isSearchVisible
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space8),
                        isVisible = !state.isSearchVisible
                    ) {
                        Banner(url = state.bannerUrl, title = state.bannerTitle)
                    }
                }
                itemsIndexed(state.products) { index, product ->
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space8),
                        isVisible = !state.isSearchVisible
                    ) {
                        if (index + 1 == state.pageNumber * 10) {
                            interactions.getMoreProducts(product.id)
                        }
                        ProductItem(
                            item = product,
                            onClick = interactions::onClickProduct
                        )
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space8),
                        isVisible = !state.isSearchVisible
                    ) {
                        if (state.isLoadMoreProducts)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colors.primary)
                            }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space8),
                        isVisible = state.isSearchVisible && state.searchContentStatus == ContentStatus.LOADING
                    ) {
                        ContentLoading(isVisible = true)
                    }
                }
                items(state.searchProducts) { product ->
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space8),
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
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        isVisible = state.isSearchVisible && state.searchContentStatus == ContentStatus.FAILURE
                    ) {
                        ContentError(isVisible = true, onTryAgain = interactions::searchForProduct)
                    }
                }
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getProducts
    )
}