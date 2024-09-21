package com.example.swiftbargain.ui.orders.view_model

sealed interface OrdersEvents {
    data object NavigateToBack : OrdersEvents
    data object UnAuthorizedAccess : OrdersEvents
    data class NavigateToOrderDetails(val id: String) : OrdersEvents
}
