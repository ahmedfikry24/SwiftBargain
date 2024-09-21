package com.example.swiftbargain.ui.orders.view_model

interface OrdersInteractions {
    fun onClickBack()
    fun getAllOrders()
    fun onClickOrder(id: String)
}
