package com.example.swiftbargain.ui.product_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.ProductReviews
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.NoItemFound
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.PrimaryTextButton
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
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showSuccess
import com.example.swiftbargain.ui.utils.UiConstants

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(effects = viewModel.event) { events, scope ->
        when (events) {
            ProductDetailsEvents.NavigateToBack -> navController.popBackStack()
            ProductDetailsEvents.NavigateToReviews -> navController.navigate(ProductReviews(state.product.id))

            ProductDetailsEvents.AddToCartSuccessfully -> snackBar.showSuccess(
                UiConstants.ADD_TO_CART_SUCCESS,
                scope
            )
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
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
                            onClickMore = interactions::onCLickMoreReviews
                        )
                }
                item {
                    if (state.reviews.isEmpty())
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = MaterialTheme.spacing.space16)
                                .padding(horizontal = MaterialTheme.spacing.space16)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.review_product),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colors.text
                                )
                                Text(
                                    modifier = Modifier.clickable { interactions.onCLickMoreReviews() },
                                    text = stringResource(R.string.add_review),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                            NoItemFound(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .align(Alignment.CenterHorizontally),
                                isVisible = true
                            )
                        }
                }
            }
            PrimaryTextButton(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space16),
                text = stringResource(R.string.add_to_cart),
                onClick = interactions::onCLickAddToCart
            )
        }
    }
    ContentError(isVisible = state.contentStatus == ContentStatus.FAILURE, onTryAgain = {})
}
