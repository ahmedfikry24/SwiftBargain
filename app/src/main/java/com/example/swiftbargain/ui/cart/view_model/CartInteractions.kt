package com.example.swiftbargain.ui.cart.view_model

interface CartInteractions {
    fun getDate()
    fun onRemoveItem(id: String)
    fun onClickItem(id: String)
    fun onChangeQuantity(index: Int, quantity: Int)
    fun onChangeCouponCode(code: String)
    fun checkCouponCode()
    fun onClickCheckOut()
}
