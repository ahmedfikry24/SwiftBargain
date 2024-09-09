package com.example.swiftbargain.ui.cart.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CartProductUiState

data class CartUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val products: List<CartProductUiState> = listOf(),
    val couponCode: String = "",
    val couponCodeError: Boolean = false,
)