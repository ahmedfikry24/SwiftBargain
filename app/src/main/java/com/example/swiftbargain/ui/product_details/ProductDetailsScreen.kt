package com.example.swiftbargain.ui.product_details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.product_details.composable.DetailsCarousal
import com.example.swiftbargain.ui.product_details.composable.DetailsColors
import com.example.swiftbargain.ui.product_details.composable.DetailsReview
import com.example.swiftbargain.ui.product_details.composable.DetailsSizes
import com.example.swiftbargain.ui.product_details.composable.DetailsTitle
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsEvents
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsInteractions
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsUiState
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { events, _ ->
        when (events) {
            ProductDetailsEvents.NavigateToBack -> navController.popBackStack()
            ProductDetailsEvents.NavigateToReviews -> Unit
        }
    }
    ProductDetailsContent(state = state, interactions = viewModel)
}

@Composable
private fun ProductDetailsContent(
    state: ProductDetailsUiState,
    interactions: ProductDetailsInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16)
        ) {
            item {
                PrimaryAppbar(
                    title = stringResource(R.string.details),
                    onClickBack = interactions::onClickBack
                )
            }
            item { DetailsCarousal(imagesUrl = state.product.url) }
            item {
                DetailsTitle(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                    title = state.product.title,
                    isFavorite = state.isFavorite,
                    rate = state.product.rate.toFloat(),
                    onCLickFavorite = interactions::onClickFavorite
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.space16,
                        top = MaterialTheme.spacing.space16
                    ),
                    text = "$ ${state.product.priceAfterDiscount}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colors.primary
                )
            }
            item {
                if (state.product.sizes.isNotEmpty())
                    DetailsSizes(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        state = state,
                        onClickSize = interactions::onClickSize
                    )
            }
            item {
                if (state.product.colors.isNotEmpty())
                    DetailsColors(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        state = state,
                        onClickColor = interactions::onCLickColor
                    )
            }

            item {
                if (state.reviews.isNotEmpty())
                    DetailsReview(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        state = state,
                        onClickMore = {}
                    )
            }
        }
    }
    ContentError(isVisible = state.contentStatus == ContentStatus.FAILURE, onTryAgain = {})
}
