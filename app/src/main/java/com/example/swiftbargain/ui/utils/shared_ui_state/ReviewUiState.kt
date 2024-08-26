package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.ReviewDto

data class ReviewUiState(
    val name: String,
    val email: String,
    val rating: String,
    val comment: String,
    val date: String
)

fun ReviewDto.toUiState(): ReviewUiState {
    return ReviewUiState(
        name = this.name,
        email = this.email,
        rating = this.rating,
        comment = this.comment,
        date = this.date
    )
}