package com.example.swiftbargain.ui.reviews.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ReviewDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.ProductReviews
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<ReviewsUiState, ReviewsEvents>(ReviewsUiState()), ReviewsInteractions {
    private val args = savedStateHandle.toRoute<ProductReviews>()

    init {
        getReviews()
    }

    override fun getReviews() {
        _state.update { it.copy(contentStatus = ReviewsUiState.ReviewContentStatus.LOADING) }
        tryExecute(
            { repository.getProductReviews(args.id) },
            ::reviewsSuccess,
            { reviewsError() }
        )
    }

    private fun reviewsSuccess(review: List<ReviewDto>) {
        val uiReviews = review.map { it.toUiState() }
        _state.update { value ->
            value.copy(
                contentStatus = ReviewsUiState.ReviewContentStatus.VISIBLE,
                allReviews = uiReviews,
                filteredReviews = uiReviews
            )
        }
    }


    private fun reviewsError() {
        _state.update { it.copy(contentStatus = ReviewsUiState.ReviewContentStatus.REVIEWS_FAILURE) }
    }

    override fun onClickBack() {

    }

    override fun onClickFilterReviews(filter: Int) {
        _state.update { value ->
            value.copy(
                selectedFilter = filter,
                filteredReviews = value.allReviews.filter { if (filter != -1) it.rating.toInt() == filter.inc() else true }
            )
        }
    }

    override fun controlSwitchContent() {
        _state.update { it.copy(isReviewsContentVisible = !it.isReviewsContentVisible) }
    }

    override fun onSelectReviewRate(rate: Int) {
        _state.update { it.copy(selectedReviewRate = rate) }
    }

    override fun onChangeReview(review: String) {
        _state.update { it.copy(reviewComment = review) }
    }

    override fun onClickAddReview() {
        if (validateReviewComment()) {
            _state.update { it.copy(contentStatus = ReviewsUiState.ReviewContentStatus.LOADING) }
            tryExecute(
                {
                    val uid = repository.getUserUid().first()
                    repository.addProductReview(
                        uid, args.id, ReviewDto(
                            rating = state.value.selectedReviewRate.toString(),
                            comment = state.value.reviewComment,
                            date = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        )
                    )
                },
                ::addReviewSuccess,
                { addReviewError() }
            )
        }
    }


    private fun addReviewSuccess(review: ReviewDto) {
        _state.update {
            it.copy(
                contentStatus = ReviewsUiState.ReviewContentStatus.VISIBLE,
                allReviews = it.allReviews.toMutableList().apply {
                    add(review.toUiState())
                },
                isReviewsContentVisible = !it.isReviewsContentVisible
            )
        }
    }

    private fun addReviewError() {
        _state.update { it.copy(contentStatus = ReviewsUiState.ReviewContentStatus.ADD_REVIEW_FAILURE) }
    }

    private fun validateReviewComment(): Boolean {
        val comment = state.value.reviewComment.validateRequireFields()
        _state.update { it.copy(reviewCommentError = !comment) }
        return comment
    }
}
