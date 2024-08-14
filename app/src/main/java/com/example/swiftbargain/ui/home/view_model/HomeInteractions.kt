package com.example.swiftbargain.ui.home.view_model

interface HomeInteractions {
    fun getData()
    fun onClickSale(id: String, title: String)
    fun onClickCategory(id: String)
    fun onClickProduct(id: String)
}
