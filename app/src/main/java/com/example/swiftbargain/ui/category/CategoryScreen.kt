package com.example.swiftbargain.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.category.view_model.CategoryEvents
import com.example.swiftbargain.ui.category.view_model.CategoryInteractions
import com.example.swiftbargain.ui.category.view_model.CategoryUiState
import com.example.swiftbargain.ui.category.view_model.CategoryViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.ControlItemVisibility
import com.example.swiftbargain.ui.composable.NoItemFound
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.controlVerticalGridPadding
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            CategoryEvents.NavigateToBack -> navController.popBackStack()
            is CategoryEvents.NavigateToProductDetails -> navController.navigate(
                ProductDetails(
                    event.id
                )
            )
        }
    }
    CategoryContent(state = state, interactions = viewModel)
}


@Composable
private fun CategoryContent(
    state: CategoryUiState,
    interactions: CategoryInteractions
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
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    PrimaryAppbar(
                        title = state.title,
                        onClickBack = interactions::onCLickBack
                    )
                }
                itemsIndexed(state.products) { index, product ->
                    if (index + 1 == state.pageNumber * 10) {
                        interactions.getMoreProducts(product.id)
                    }
                    val isRightSide = index % 2 == 0
                    ProductItem(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacing.space16)
                            .controlVerticalGridPadding(isRightSide),
                        item = product,
                        onClick = interactions::onClickProduct
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    NoItemFound(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = MaterialTheme.spacing.space16),
                        isVisible = state.products.isEmpty()
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ControlItemVisibility(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.space16),
                        isVisible = state.isLoadMoreProducts
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colors.primary)
                        }
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
