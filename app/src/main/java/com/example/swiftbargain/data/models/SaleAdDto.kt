package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SaleAdDto(
    val id: String = "",
    val title: String = "",
    val type: String = "",
    val url: String = ""
)
