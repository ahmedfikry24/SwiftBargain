package com.example.swiftbargain.data.models

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String = "",
    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var categoryId: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val recommended: Boolean = false,
    @get:PropertyName("discount_percentage")
    @set:PropertyName("discount_percentage")
    var discountPercentage: String = "",
    val rate: String = "",
    val url: List<String> = listOf()
)
