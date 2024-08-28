package com.example.swiftbargain.ui.reviews.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor() :
    BaseViewModel<ReviewsUiState, ReviewsEvents>(ReviewsUiState()), ReviewsInteractions
