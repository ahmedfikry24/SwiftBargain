package com.example.swiftbargain.ui.reviews.view_model

sealed interface ReviewsEvents {
    data object NavigateToBack : ReviewsEvents
    data object ReviewAddedSuccess : ReviewsEvents
}
