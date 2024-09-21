package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.OrderDto

data class OrderUiState(
    val id: String = "",
    val date: String = "",
    val status: OrderStatus = OrderStatus.PACKING,
    var productsId: List<String> = listOf(),
    val numOfItems: String = "",
    val price: Int = 0,
    val address: AddressUiState = AddressUiState()
) {
    enum class OrderStatus(val value: String) {
        PACKING("Packing"),
        SHIPPING("Shipping"),
        ARRIVING("Arriving"),
        SUCCESS("Success")
    }
}

fun OrderDto.toUiState(): OrderUiState {
    return OrderUiState(
        id = this.id,
        date = this.date,
        status = getOrderStatus(this.status),
        productsId = this.productsId,
        numOfItems = this.numOfItems,
        price = this.price,
        address = this.address.toUiState()
    )
}

private const val PACKING = "Packing"
private const val SHIPPING = "Shipping"
private const val ARRIVING = "Arriving"

private fun getOrderStatus(status: String): OrderUiState.OrderStatus {
    return when (status) {
        PACKING -> OrderUiState.OrderStatus.PACKING
        SHIPPING -> OrderUiState.OrderStatus.SHIPPING
        ARRIVING -> OrderUiState.OrderStatus.ARRIVING
        else -> OrderUiState.OrderStatus.SUCCESS
    }
}

fun OrderUiState.toDto(): OrderDto {
    return OrderDto(
        id = this.id,
        date = this.date,
        status = this.status.value,
        productsId = this.productsId,
        numOfItems = this.numOfItems,
        price = this.price,
        address = this.address.toDto()
    )
}
