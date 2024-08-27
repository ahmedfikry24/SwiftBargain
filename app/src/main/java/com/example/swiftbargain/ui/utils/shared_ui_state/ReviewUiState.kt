package com.example.swiftbargain.ui.utils.shared_ui_state

import android.os.Parcelable
import com.example.swiftbargain.data.models.ReviewDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ReviewUiState(
    val name: String,
    val email: String,
    val rating: String,
    val comment: String,
    val date: String
) : Parcelable

fun ReviewDto.toUiState(): ReviewUiState {
    return ReviewUiState(
        name = this.name,
        email = this.email,
        rating = this.rating,
        comment = this.comment,
        date = this.date
    )
}