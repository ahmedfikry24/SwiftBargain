package com.example.swiftbargain.ui.cart.view_model

interface CartInteractions {
    fun onRemoveItem(id: String)
    fun onClickItem(id: String)
    fun onChangeQuantity(index: Int, quantity: Int)
}
