package com.example.swiftbargain.ui.utils.shared_ui_state

data class AddAddressUiState(
    val country: String = "",
    val countryError: Boolean = false,
    val firstName: String = "",
    val firstNameError: Boolean = false,
    val lastname: String = "",
    val lastnameError: Boolean = false,
    val streetAddress: String = "",
    val streetAddressError: Boolean = false,
    val streetAddress2: String = "",
    val city: String = "",
    val cityError: Boolean = false,
    val region: String = "",
    val regionError: Boolean = false,
    val zipCode: String = "",
    val zipCodeError: Boolean = false,
    val phone: String = "",
    val phoneError: Boolean = false,
)
