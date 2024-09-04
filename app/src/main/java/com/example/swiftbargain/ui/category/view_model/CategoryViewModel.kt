package com.example.swiftbargain.ui.category.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() :
    BaseViewModel<CategoryUiState, CategoryEvents>(CategoryUiState()), CategoryInteractions
