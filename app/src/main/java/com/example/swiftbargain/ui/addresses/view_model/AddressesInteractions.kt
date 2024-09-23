package com.example.swiftbargain.ui.addresses.view_model

import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

interface AddressesInteractions : AddAddressInteractions {
    fun getAllAddresses()
    fun onCLickBack()
    fun controlAddAddressVisibility()
    fun controlDeleteAddressDialogVisibility()
    fun onSelectDeleteAddress(address: AddressUiState)
    fun onDeleteAddress()
}
