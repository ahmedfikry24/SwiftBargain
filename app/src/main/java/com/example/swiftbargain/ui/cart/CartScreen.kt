package com.example.swiftbargain.ui.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.cart.view_model.CartInteractions
import com.example.swiftbargain.ui.cart.view_model.CartUiState
import com.example.swiftbargain.ui.cart.view_model.CartViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

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
        LazyColumn(modifier = Modifier.fillMaxSize()) {

        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = {}
    )
}