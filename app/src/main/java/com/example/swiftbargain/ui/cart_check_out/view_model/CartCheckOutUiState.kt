package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddAddressUiState

data class CartCheckOutUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val visibleContent: VisibleContent = VisibleContent.SHIP_TO,
    val allAddresses: List<AddAddressUiState> = listOf(),
    val selectedAddress: AddAddressUiState = AddAddressUiState(),
    val isAddAddressVisible: Boolean = false,
    val addAddressUiState: AddAddressUiState = AddAddressUiState(),
) {
    enum class VisibleContent {
        SHIP_TO,
        PAYMENT,
        CHOOSE_CARD
    }
}
