package com.example.swiftbargain.ui.cart_check_out

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.cart_check_out.composable.CheckOutPaymentContent
import com.example.swiftbargain.ui.cart_check_out.composable.ChooseCardContent
import com.example.swiftbargain.ui.cart_check_out.composable.ShipToContent
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun CartCheckOutScreen(
    navController: NavController,
    viewModel: CartCheckOutViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

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
