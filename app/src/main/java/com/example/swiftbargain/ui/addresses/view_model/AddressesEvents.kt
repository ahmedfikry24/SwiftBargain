package com.example.swiftbargain.ui.addresses.view_model

sealed interface AddressesEvents {
    data object NavigateToBack : AddressesEvents
    data object UnAuthorizedAccess : AddressesEvents
    data object AddAddressSuccess : AddressesEvents
}
