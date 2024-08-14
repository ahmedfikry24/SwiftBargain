package com.example.swiftbargain.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.Banner
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.LifeCycleTracker
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.composable.SearchBar
import com.example.swiftbargain.ui.home.compsable.Carousal
import com.example.swiftbargain.ui.home.compsable.HomeCategories
import com.example.swiftbargain.ui.home.compsable.HomeSaleSection
import com.example.swiftbargain.ui.home.view_model.HomeInteractions
import com.example.swiftbargain.ui.home.view_model.HomeUiState
import com.example.swiftbargain.ui.home.view_model.HomeViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDataArrive by remember { mutableStateOf(false) }
    LifeCycleTracker { event ->
        if (event == Lifecycle.Event.ON_CREATE && !isDataArrive) {
            viewModel.getData()
            isDataArrive = true
        }
    }
    EventHandler(effects = viewModel.event) { event, scope ->
        when (event) {

            else -> Unit
        }
    }
    HomeContent(state = state, interactions = viewModel)
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    interactions: HomeInteractions
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
                SearchBar(
                    value = state.search,
                    onClickKeyboardDone = {},
                    onChangeValue = {},
                    onClickFavourite = {},
                    onClickNotification = {}
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Carousal(items = state.saleAds)
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (state.categories.isNotEmpty())
                    HomeCategories(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        categories = state.categories,
                        onCLick = {}
                    )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (state.flashSale.isNotEmpty())
                    HomeSaleSection(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        sectionName = stringResource(R.string.flash_sale),
                        items = state.flashSale,
                        onCLickMore = {},
                        onClickItem = {}
                    )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (state.megaSale.isNotEmpty())
                    HomeSaleSection(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        sectionName = stringResource(R.string.mega_sale),
                        items = state.megaSale,
                        onCLickMore = {},
                        onClickItem = {}
                    )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Banner(
                    url = state.saleAds.last().url,
                    title = stringResource(R.string.recommended_product)
                )
            }
            items(state.recommendedProducts) { product ->
                ProductItem(
                    item = product,
                    isRateVisible = true,
                    onClick = {}
                )
            }
        }
    }

    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getData
    )
}