package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val id: String = "",
    val name: String? = "",
    val email: String? = "",
    val imageUrl: String? = "",
    val gender: String = "Male",
    val birthday: String = "",
    val phone: String? = "",
    val addresses: List<AddressDto> = listOf()
)
