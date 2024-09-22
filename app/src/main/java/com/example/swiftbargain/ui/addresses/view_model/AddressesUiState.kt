package com.example.swiftbargain.ui.addresses.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState

data class AddressesUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val allAddresses: List<AddressUiState> = listOf(),
    val isAddAddressVisible: Boolean = false,
    val addAddress: AddressUiState = AddressUiState(),
    val selectedAddress: AddressUiState = AddressUiState(),
    val isDeleteAddressVisible: Boolean = false
) 
