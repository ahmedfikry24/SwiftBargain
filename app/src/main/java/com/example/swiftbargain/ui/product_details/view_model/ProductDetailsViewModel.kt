package com.example.swiftbargain.ui.product_details.view_model

import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<ProductDetailsUiState, ProductDetailsEvents>(ProductDetailsUiState()),
    ProductDetailsInteractions
