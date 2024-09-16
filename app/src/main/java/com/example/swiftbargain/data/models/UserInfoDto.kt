package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val id: String = "",
    val name: String? = null,
    val email: String? = null,
    val addresses: List<AddressDto> = listOf()
)
