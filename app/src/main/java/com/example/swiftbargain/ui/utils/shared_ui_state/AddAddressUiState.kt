package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.ui.utils.ContentStatus

data class AddAddressUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val country: String = "",
    val countryError: Boolean = false,
    val name: String = "",
    val nameError: Boolean = false,
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

fun AddAddressUiState.toDto(): UserInfoDto.AddressInfo {
    return UserInfoDto.AddressInfo(
        country = this.country,
        name = this.name,
        streetAddress = this.streetAddress,
        streetAddress2 = this.streetAddress2,
        city = this.city,
        region = this.region,
        zipCode = this.zipCode,
        phone = this.phone
    )
}

fun UserInfoDto.AddressInfo.toUiState(): AddAddressUiState {
    return AddAddressUiState(
        country = this.country,
        name = this.name,
        streetAddress = this.streetAddress,
        streetAddress2 = this.streetAddress2 ?: "",
        city = this.city,
        region = this.region,
        zipCode = this.zipCode,
        phone = this.phone
    )
}

