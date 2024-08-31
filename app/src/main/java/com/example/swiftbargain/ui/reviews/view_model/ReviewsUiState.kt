package com.example.swiftbargain.ui.reviews.view_model

import com.example.swiftbargain.ui.utils.shared_ui_state.ReviewUiState

data class ReviewsUiState(
    val contentStatus: ReviewContentStatus = ReviewContentStatus.VISIBLE,
    val isReviewsContentVisible: Boolean = true,
    val allReviews: List<ReviewUiState> = listOf(),
    val selectedFilter: Int = -1,
    val filteredReviews: List<ReviewUiState> = listOf(),
    val selectedReviewRate: Int = 1,
    val reviewComment: String = "",
    val reviewCommentError: Boolean = false
) {
    enum class ReviewContentStatus {
        LOADING,
        VISIBLE,
        REVIEWS_FAILURE,
        ADD_REVIEW_FAILURE
    }
}
