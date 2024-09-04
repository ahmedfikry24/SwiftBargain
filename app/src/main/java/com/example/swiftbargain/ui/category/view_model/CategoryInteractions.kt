package com.example.swiftbargain.ui.category.view_model

interface CategoryInteractions {
    fun getProducts()
    fun onCLickBack()
    fun onClickProduct(id: String)
    fun getMoreProducts(id: String?)
}
