package com.example.swiftbargain.ui.reviews.view_model

interface ReviewsInteractions {
    fun getReviews()
    fun onClickBack()
    fun onClickFilterReviews(filter: Int)
    fun controlSwitchContent()
    fun onSelectReviewRate(rate: Int)
    fun onChangeReview(review: String)
    fun onClickAddReview()
}
