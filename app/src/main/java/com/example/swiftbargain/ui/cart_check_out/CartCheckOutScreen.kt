package com.example.swiftbargain.ui.cart_check_out

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.navigation.Cart
import com.example.swiftbargain.ui.cart_check_out.composable.CheckOutPaymentContent
import com.example.swiftbargain.ui.cart_check_out.composable.ChooseCardContent
import com.example.swiftbargain.ui.cart_check_out.composable.ShipToContent
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutEvents
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showSuccess
import com.example.swiftbargain.ui.utils.UiConstants

@Composable
fun CartCheckOutScreen(
    navController: NavController,
    unAuthorizedLogin: () -> Unit,
    viewModel: CartCheckOutViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(effects = viewModel.event) { event, scope ->
        when (event) {
            CartCheckOutEvents.AddCreditSuccess -> snackBar.showSuccess(
                UiConstants.ADD_CREDIT_SUCCESS,
                scope
            )

            CartCheckOutEvents.NavigateToBack -> navController.popBackStack()
            CartCheckOutEvents.OrderSuccess -> {
                snackBar.showSuccess(
                    UiConstants.ORDER_SUCCESS,
                    scope
                ) {
                    navController.navigate(Cart(true)) {
                        popUpTo(Cart()) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            CartCheckOutEvents.UnAuthorizedToAccess -> unAuthorizedLogin()
            CartCheckOutEvents.AddAddressSuccess -> snackBar.showSuccess(
                UiConstants.ADD_ADDRESS_SUCCESS,
                scope
            )
        }
    }
    CartCheckOutContent(state = state, interactions = viewModel)
}

@Composable
private fun CartCheckOutContent(
    state: CartCheckOutUiState,
    interactions: CartCheckOutInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        BackHandler(
            enabled = state.visibleContent.isBackEnabled,
        ) {
            when (state.visibleContent) {
                CartCheckOutUiState.VisibleContent.SHIP_TO -> interactions.onClickBack()
                CartCheckOutUiState.VisibleContent.PAYMENT -> interactions.onSwitchContent(
                    CartCheckOutUiState.VisibleContent.SHIP_TO
                )

                CartCheckOutUiState.VisibleContent.CHOOSE_CARD -> interactions.onSwitchContent(
                    CartCheckOutUiState.VisibleContent.PAYMENT
                )
            }
        }
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = state.visibleContent,
            label = "transition"
        ) { content ->
            when (content) {
                CartCheckOutUiState.VisibleContent.SHIP_TO -> ShipToContent(
                    state = state,
                    interactions = interactions
                )

                CartCheckOutUiState.VisibleContent.PAYMENT -> CheckOutPaymentContent(
                    payment = state.paymentMethod,
                    interactions = interactions
                )

                CartCheckOutUiState.VisibleContent.CHOOSE_CARD -> ChooseCardContent(
                    state = state,
                    interactions = interactions
                )
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getData
    )
}
