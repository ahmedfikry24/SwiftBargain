package com.example.swiftbargain.ui.order_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.order_details.composable.OrderProducts
import com.example.swiftbargain.ui.order_details.composable.OrderStatus
import com.example.swiftbargain.ui.order_details.composable.PaymentDetails
import com.example.swiftbargain.ui.order_details.composable.ShippingDetails
import com.example.swiftbargain.ui.order_details.view_model.OrderDetailsEvents
import com.example.swiftbargain.ui.order_details.view_model.OrderDetailsInteractions
import com.example.swiftbargain.ui.order_details.view_model.OrderDetailsUiState
import com.example.swiftbargain.ui.order_details.view_model.OrderDetailsViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    unAuthorizedLogin: () -> Unit,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            OrderDetailsEvents.NavigateToBack -> navController.popBackStack()
            is OrderDetailsEvents.NavigateToProductDetails -> navController.navigate(
                ProductDetails(
                    event.id
                )
            )

            OrderDetailsEvents.UnAuthorizedAccess -> unAuthorizedLogin()
        }
    }
    OrderDetailsContent(state = state, interactions = viewModel)
}

@Composable
private fun OrderDetailsContent(
    state: OrderDetailsUiState,
    interactions: OrderDetailsInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            item {
                PrimaryAppbar(
                    title = stringResource(R.string.order_details),
                    onClickBack = interactions::onClickBack
                )
            }
            item {
                OrderStatus(status = state.order.status)
            }
            item {
                OrderProducts(
                    products = state.products,
                    onClick = interactions::onClickProduct
                )
            }
            item {
                ShippingDetails(order = state.order, products = state.products)
            }
            item {
                PaymentDetails(order = state.order)
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getOrderDetails
    )
}
