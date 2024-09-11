package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val id: String = "",
    val name: String? = null,
    val email: String? = null,
    val addresses: List<AddressInfo> = listOf()
) {
    @Serializable
    data class AddressInfo(
        val country: String = "",
        val name: String = "",
        val streetAddress: String = "",
        val streetAddress2: String? = null,
        val city: String = "",
        val region: String = "",
        val zipCode: String = "",
        val phone: String = ""
    )
}
