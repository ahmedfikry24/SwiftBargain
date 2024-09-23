package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.CartProductUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.OrderUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod

data class CartCheckOutUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val visibleContent: VisibleContent = VisibleContent.SHIP_TO,
    val allAddresses: List<AddressUiState> = listOf(),
    val selectedAddress: AddressUiState = AddressUiState(),
    val isAddAddressVisible: Boolean = false,
    val addAddressState: AddressUiState = AddressUiState(),
    val isDeleteAddressVisible: Boolean = false,
    val selectedDeleteAddress: AddressUiState = AddressUiState(),
    val paymentMethod: PaymentMethod = PaymentMethod.DEFAULT,
    val chooseCardContentStatus: ContentStatus = ContentStatus.VISIBLE,
    val allCreditCards: List<CreditUiSate> = listOf(),
    val selectedCreditCard: CreditUiSate = CreditUiSate(),
    val isAddCreditCardVisible: Boolean = false,
    val addCreditState: CreditUiSate = CreditUiSate(),
    val allCartProducts: List<CartProductUiState> = listOf(),
    val currentOder: OrderUiState = OrderUiState()
) {
    enum class VisibleContent(val isBackEnabled: Boolean) {
        SHIP_TO(false),
        PAYMENT(true),
        CHOOSE_CARD(true)
    }
}
