package com.example.swiftbargain.ui.reviews

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.reviews.composable.ReviewsMainContent
import com.example.swiftbargain.ui.reviews.composable.WriteReviewContent
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
    ContentLoading(isVisible = state.contentStatus == ReviewsUiState.ReviewContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ReviewsUiState.ReviewContentStatus.VISIBLE) {
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = state.isReviewsContentVisible,
            label = "transition"
        ) { value ->
            if (value)
                ReviewsMainContent(state = state, interactions = interactions)
            else
                WriteReviewContent(state = state, interactions = interactions)
        }
    }
    ContentError(
        isVisible = state.contentStatus == ReviewsUiState.ReviewContentStatus.REVIEWS_FAILURE,
        onTryAgain = interactions::getReviews
    )
    ContentError(
        isVisible = state.contentStatus == ReviewsUiState.ReviewContentStatus.ADD_REVIEW_FAILURE,
        onTryAgain = interactions::onClickAddReview
    )
}