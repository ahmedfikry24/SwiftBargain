package com.example.swiftbargain.ui.sale.view_model

interface SaleInteractions {
    fun getProducts()
    fun getMoreProducts(lastItemId: String? = null)
    fun onClickBack()
    fun controlSearchVisibility()
    fun onChangeSearch(search: String)
    fun searchForProduct()
    fun onClickProduct(id: String)
}
