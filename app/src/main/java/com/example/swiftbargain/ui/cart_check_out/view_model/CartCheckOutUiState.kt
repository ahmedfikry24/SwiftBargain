package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState

data class CartCheckOutUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val visibleContent: VisibleContent = VisibleContent.SHIP_TO,
    val allAddresses: List<AddressUiState> = listOf(),
    val selectedAddress: AddressUiState = AddressUiState(),
    val isAddAddressVisible: Boolean = false,
    val addAddressState: AddressUiState = AddressUiState(),
    val isDeleteAddressVisible: Boolean = false,
    val selectedDeleteAddress: AddressUiState = AddressUiState()
) {
    enum class VisibleContent {
        SHIP_TO,
        PAYMENT,
        CHOOSE_CARD
    }
}
