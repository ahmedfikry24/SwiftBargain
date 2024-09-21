package com.example.swiftbargain.ui.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.OrderDetails
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.orders.composable.OrderItem
import com.example.swiftbargain.ui.orders.view_model.OrdersEvents
import com.example.swiftbargain.ui.orders.view_model.OrdersInteractions
import com.example.swiftbargain.ui.orders.view_model.OrdersUiState
import com.example.swiftbargain.ui.orders.view_model.OrdersViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import kotlinx.coroutines.launch

@Composable
fun OrdersScreen(
    navController: NavController,
    unAuthorizedLogin: () -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            OrdersEvents.NavigateToBack -> navController.popBackStack()
            is OrdersEvents.NavigateToOrderDetails -> navController.navigate(OrderDetails(event.id))
            OrdersEvents.UnAuthorizedAccess -> unAuthorizedLogin()
        }
    }
    OrdersContent(state = state, interactions = viewModel)
}

@Composable
private fun OrdersContent(
    state: OrdersUiState,
    interactions: OrdersInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()
        ScrollToFirstItemFab(
            modifier = Modifier.fillMaxSize(),
            isFabVisible = scrollState.canScrollBackward,
            onClickFab = { scope.launch { scrollState.animateScrollTo(0) } }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
            ) {
                item {
                    PrimaryAppbar(
                        title = stringResource(R.string.orders),
                        onClickBack = interactions::onClickBack
                    )
                }
                items(state.orders) { order ->
                    OrderItem(
                        order = order,
                        onClick = { interactions.onClickOrder(order.id) }
                    )
                }
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getAllOrders
    )
}
