package com.example.swiftbargain.ui.sale.view_model

interface SaleInteractions {
    fun getProducts()
    fun getMoreProducts(lastItemId: String? = null)
    fun onClickBack()
    fun onClickSearch()
    fun onClickProduct(id: String)
}
