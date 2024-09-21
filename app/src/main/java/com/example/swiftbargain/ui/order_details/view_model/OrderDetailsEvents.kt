package com.example.swiftbargain.ui.order_details.view_model

sealed interface OrderDetailsEvents {
    data object NavigateToBack : OrderDetailsEvents
    data object UnAuthorizedAccess : OrderDetailsEvents
    data class NavigateToProductDetails(val id: String) : OrderDetailsEvents
}
