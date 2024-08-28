package com.example.swiftbargain.ui.reviews.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.ReviewUiState

data class ReviewsUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val isReviewsContentVisible: Boolean = true,
    val allReviews: List<ReviewUiState> = listOf(),
    val selectedFilter: Int = 0,
    val filteredReviews: List<ReviewUiState> = listOf()
) 
