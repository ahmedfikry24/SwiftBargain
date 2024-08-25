package com.example.swiftbargain.ui.product_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsInteractions
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsUiState
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsViewModel


@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProductDetailsContent(state = state, interactions = viewModel)
}

@Composable
private fun ProductDetailsContent(
    state: ProductDetailsUiState,
    interactions: ProductDetailsInteractions
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}
