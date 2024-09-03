package com.example.swiftbargain.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val name: String = "",
    val email: String = "",
    val rating: String = "",
    val comment: String = "",
    val date: String = ""
)


