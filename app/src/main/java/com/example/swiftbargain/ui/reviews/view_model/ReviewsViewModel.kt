package com.example.swiftbargain.ui.reviews.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ReviewDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.ProductReviews
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
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
                contentStatus = ContentStatus.VISIBLE,
                allReviews = uiReviews,
                filteredReviews = uiReviews
            )
        }
    }


    private fun reviewsError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickBack() {

    }

    override fun onClickFilterReviews(filter: Int) {
        _state.update { value ->
            value.copy(
                selectedFilter = filter,
                filteredReviews = value.allReviews.filter { if (filter != -1) it.rating.toInt() == filter else true }
            )
        }
    }

}
