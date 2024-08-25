package com.example.swiftbargain.ui.product_details.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class ProductDetailsUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val product: ProductUiState = ProductUiState(),
    val isFavorite: Boolean = false,
    val selectedSize: String = "",
    val selectedColor: Long = 0L,
)
