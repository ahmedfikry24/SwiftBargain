package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

interface CartCheckOutInteractions : AddAddressInteractions {
    fun getAllAddress()
    fun onClickBack()
    fun controlAddAddressVisibility()
    fun onSelectAddress(address: AddressUiState)
    fun onDeleteAddress(address: AddressUiState)
    fun deleteAddress()
    fun onDismissDeleteAddressDialog()
}
