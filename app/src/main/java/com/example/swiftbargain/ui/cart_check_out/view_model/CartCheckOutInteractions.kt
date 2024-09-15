package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions
import com.example.swiftbargain.ui.utils.shred_interactions.AddCreditInteractions

interface CartCheckOutInteractions : AddAddressInteractions, AddCreditInteractions {
    fun getAllAddress()
    fun onClickBack()
    fun controlAddAddressVisibility()
    fun onSelectAddress(address: AddressUiState)
    fun onDeleteAddress(address: AddressUiState)
    fun deleteAddress()
    fun onDismissDeleteAddressDialog()
    fun onSwitchContent(content: CartCheckOutUiState.VisibleContent)
    fun onChoosePaymentMethodL(payment: PaymentMethod)
    fun checkOutOder()
    fun controlAddCreditVisibility()
}
