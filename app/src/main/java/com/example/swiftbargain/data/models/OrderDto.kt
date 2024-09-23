package com.example.swiftbargain.data.models

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String = "",
    val date: String = "",
    val status: String = "",
    @get:PropertyName("products_id")
    @set:PropertyName("products_id")
    var productsId: List<String> = listOf(),
    val numOfItems: String = "",
    val price: Int = 0,
    val address: AddressDto = AddressDto()
)
