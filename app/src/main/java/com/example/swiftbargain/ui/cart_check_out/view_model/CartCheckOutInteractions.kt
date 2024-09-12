package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.shared_ui_state.AddAddressUiState
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

interface CartCheckOutInteractions : AddAddressInteractions {
    fun getAllAddress()
    fun onClickBack()
    fun controlAddAddressVisibility()
    fun onSelectAddress(address: AddAddressUiState)
    fun onDeleteAddress(address: AddAddressUiState)
}
