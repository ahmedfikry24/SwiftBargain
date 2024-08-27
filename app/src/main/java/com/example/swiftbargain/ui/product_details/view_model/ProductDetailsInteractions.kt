package com.example.swiftbargain.ui.product_details.view_model

interface ProductDetailsInteractions {
    fun getData()
    fun onClickBack()
    fun onClickFavorite()
    fun onClickSize(size: String)
    fun onCLickColor(color: Long)
    fun onCLickMoreReviews()
    fun onCLickAddToCart()
}
