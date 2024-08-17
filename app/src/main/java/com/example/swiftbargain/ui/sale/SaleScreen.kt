package com.example.swiftbargain.ui.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.swiftbargain.ui.composable.LifeCycleTracker
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.sale.composable.SaleAppBar
import com.example.swiftbargain.ui.sale.view_model.SaleEvents
import com.example.swiftbargain.ui.sale.view_model.SaleInteractions
import com.example.swiftbargain.ui.sale.view_model.SaleUiState
import com.example.swiftbargain.ui.sale.view_model.SaleViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

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
            isDataArrive = true
        }
    }
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            SaleEvents.GoBack -> navController.popBackStack()
            SaleEvents.GoToSearch -> Unit
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
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SaleAppBar(
                    title = state.title,
                    onClickBack = interactions::onClickBack,
                    onClickSearch = interactions::onClickSearch
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Banner(url = state.bannerUrl, title = state.bannerTitle)
            }

            itemsIndexed(state.products) { index, product ->
                if (index + 1 == state.pageNumber * 10) {
                    interactions.getMoreProducts(product.id)
                }
                ProductItem(
                    item = product,
                    onClick = interactions::onClickProduct
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (state.isLoadMoreProducts)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
            }
        }

    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getProducts
    )
}