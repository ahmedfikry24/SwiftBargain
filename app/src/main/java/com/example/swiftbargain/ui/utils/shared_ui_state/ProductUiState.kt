package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity
import com.example.swiftbargain.data.models.ProductDto

data class ProductUiState(
    val id: String = "",
    val categoryId: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val priceAfterDiscount: String = "",
    val discountPercentage: String = "",
    val saleId: String = "",
    val rate: String = "",
    val url: List<String> = listOf(),
    val sizes: List<String> = listOf(),
    val colors: List<Long> = listOf()
)

fun ProductDto.toUiState(): ProductUiState {
    return ProductUiState(
        id = this.id,
        categoryId = this.categoryId,
        title = this.title,
        description = this.description,
        price = this.price,
        priceAfterDiscount = calculateDiscountedPrice(
            this.price.toDouble(),
            this.discountPercentage.toDouble()
        ).toString(),
        discountPercentage = this.discountPercentage,
        saleId = this.saleId,
        rate = this.rate,
        url = this.url,
        sizes = this.sizes,
        colors = this.colors
    )
}

fun calculateDiscountedPrice(price: Double, discountPercentage: Double): Int {
    val discountAmount = price * (discountPercentage / 100)
    return (price - discountAmount).toInt()
}

fun FavoriteProductEntity.toUiState(): ProductUiState {
    return ProductUiState(
        id = this.id,
        title = this.name,
        rate = this.rate,
        price = this.price,
        priceAfterDiscount = this.priceAfterDiscount,
        discountPercentage = this.discountPercentage,
        url = listOf(imageUrl)
    )
}

fun ProductUiState.toEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = this.id,
        name = this.title,
        rate = this.rate,
        price = this.price,
        priceAfterDiscount = this.priceAfterDiscount,
        discountPercentage = this.discountPercentage,
        imageUrl = this.url.first()
    )
}
