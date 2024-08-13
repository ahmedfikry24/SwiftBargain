package com.example.swiftbargain.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            item {
                SearchBar(
                    value = state.search,
                    onClickKeyboardDone = { },
                    onChangeValue = {},
                    onClickFavourite = { },
                    onClickNotification = {}
                )
            }
            item {
                Carousal(items = state.saleAds)
            }
            item {
                if (state.categories.isNotEmpty())
                    HomeCategories(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        categories = state.categories
                    )
            }
            item {
                if (state.flashSale.isNotEmpty())
                    HomeSaleSection(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        sectionName = stringResource(R.string.flash_sale),
                        items = state.flashSale
                    )
            }
            item {
                if (state.megaSale.isNotEmpty())
                    HomeSaleSection(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        sectionName = stringResource(R.string.mega_sale),
                        items = state.megaSale
                    )
            }
        }
    }
}