package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.local.room.entity.CartProductEntity

data class CartProductUiState(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val size: String = "",
    val color: String = "",
    val quantity: String = "",
    val orderQuantity: Int = 1,
)

fun CartProductEntity.toUiState(): CartProductUiState {
    return CartProductUiState(
        id = this.id,
        name = this.name,
        price = this.price,
        color = this.color,
        size = this.size,
        imageUrl = this.imageUrl,
        quantity = this.quantity,
        orderQuantity = this.orderQuantity
    )
}

fun CartProductUiState.toEntity(): CartProductEntity {
    return CartProductEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        color = this.color,
        size = this.size,
        imageUrl = this.imageUrl,
        quantity = this.quantity,
        orderQuantity = this.orderQuantity
    )
}