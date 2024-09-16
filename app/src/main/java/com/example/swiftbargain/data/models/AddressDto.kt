package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val country: String = "",
    val name: String = "",
    val streetAddress: String = "",
    val streetAddress2: String? = null,
    val city: String = "",
    val zipCode: String = "",
    val phone: String = ""
)
