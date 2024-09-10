package com.example.swiftbargain.ui.cart.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CartProductUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.CouponCodeUiState

data class CartUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val products: List<CartProductUiState> = listOf(),
    val coupons: List<CouponCodeUiState> = listOf(),
    val couponDiscount: String = "",
    val couponCode: String = "",
    val couponCodeError: Boolean = false,
    val productsPrice: Int = 0,
    val totalPrice: Int = 0
)