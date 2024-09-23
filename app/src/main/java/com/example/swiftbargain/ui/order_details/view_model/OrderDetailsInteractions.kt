package com.example.swiftbargain.ui.order_details.view_model

interface OrderDetailsInteractions {
    fun onClickBack()
    fun getOrderDetails()
    fun onClickProduct(id: String)
}
