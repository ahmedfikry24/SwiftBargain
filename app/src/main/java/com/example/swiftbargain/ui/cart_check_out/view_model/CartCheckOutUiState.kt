package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.ContentStatus

data class CartCheckOutUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val visibleContent: VisibleContent = VisibleContent.SHIP_TO,
    val isAddAddressVisible: Boolean = false,
) {
    enum class VisibleContent {
        SHIP_TO,
        PAYMENT,
        CHOOSE_CARD
    }
}
