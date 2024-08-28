package com.example.swiftbargain.ui.reviews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.reviews.view_model.ReviewsInteractions
import com.example.swiftbargain.ui.reviews.view_model.ReviewsUiState
import com.example.swiftbargain.ui.reviews.view_model.ReviewsViewModel
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun ReviewsScreen(
    navController: NavController,
    viewModel: ReviewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
    ReviewsContent(state = state, interactions = viewModel)
}


@Composable
private fun ReviewsContent(
    state: ReviewsUiState,
    interactions: ReviewsInteractions
) {

}