package com.example.swiftbargain.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.CartCheckOut
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.cart.composable.CartDetails
import com.example.swiftbargain.ui.cart.composable.CartProduct
import com.example.swiftbargain.ui.cart.view_model.CartEvents
import com.example.swiftbargain.ui.cart.view_model.CartInteractions
import com.example.swiftbargain.ui.cart.view_model.CartUiState
import com.example.swiftbargain.ui.cart.view_model.CartViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.NoItemFound
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            CartEvents.NavigateToCartCheckOut -> navController.navigate(CartCheckOut(state.totalPrice))
            is CartEvents.NavigateToProductDetails -> navController.navigate(ProductDetails(event.id))
        }
    }
    CartContent(state = state, interactions = viewModel)
}

@Composable
fun CartContent(
    state: CartUiState,
    interactions: CartInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            itemsIndexed(state.products) { index, product ->
                CartProduct(
                    modifier = Modifier.animateItem(),
                    product = product,
                    onClickRemove = interactions::onRemoveItem,
                    onChangeQuantity = { interactions.onChangeQuantity(index, it) },
                    onClickItem = interactions::onClickItem
                )
            }
            item {
                NoItemFound(
                    modifier = Modifier.fillMaxSize(),
                    isVisible = state.products.isEmpty()
                )
            }
            item {
                if (state.products.isNotEmpty())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.spacing.space16)
                            .heightIn(max = 56.dp)
                    ) {
                        PrimaryTextField(
                            modifier = Modifier.weight(1f),
                            value = state.couponCode,
                            hint = stringResource(R.string.enter_coupon_code),
                            isError = state.couponCodeError,
                            onChangeValue = interactions::onChangeCouponCode
                        )
                        PrimaryTextButton(
                            modifier = Modifier.fillMaxHeight(),
                            text = stringResource(R.string.apply),
                            onClick = interactions::checkCouponCode
                        )
                    }
            }

            item {
                if (state.products.isNotEmpty())
                    CartDetails(state = state)
            }
            item {
                PrimaryTextButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.check_out),
                    onClick = {}
                )
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getDate
    )
}