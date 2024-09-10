package com.example.swiftbargain.ui.cart.view_model

sealed interface CartEvents {
    data class NavigateToProductDetails(val id: String) : CartEvents
    data object NavigateToCartCheckOut : CartEvents
}
