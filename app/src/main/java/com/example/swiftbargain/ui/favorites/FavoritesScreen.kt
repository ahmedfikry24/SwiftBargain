package com.example.swiftbargain.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.ProductItem
import com.example.swiftbargain.ui.favorites.view_model.FavoritesEvents
import com.example.swiftbargain.ui.favorites.view_model.FavoritesInteractions
import com.example.swiftbargain.ui.favorites.view_model.FavoritesUiState
import com.example.swiftbargain.ui.favorites.view_model.FavoritesViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            FavoritesEvents.NavigateToBack -> navController.popBackStack()
            is FavoritesEvents.NavigateToProductDetails -> navController.navigate(
                ProductDetails(
                    event.id
                )
            )
        }
    }
    FavoritesContent(state = state, interactions = viewModel)
}

@Composable
private fun FavoritesContent(
    state: FavoritesUiState,
    interactions: FavoritesInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        Column(modifier = Modifier.fillMaxSize()) {
            PrimaryAppbar(
                modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                title = stringResource(R.string.favorites),
                onClickBack = interactions::onClickBack
            )
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12),
                contentPadding = PaddingValues(MaterialTheme.spacing.space16)
            ) {
                items(state.favorites) { item ->
                    ProductItem(
                        item = item,
                        isFavIconVisible = true,
                        onClickFavIcon = interactions::onRemoveFavorite,
                        onClick = interactions::onClickProduct
                    )
                }
            }
        }
    }
}