package com.example.swiftbargain.ui.explore.view_model

interface ExploreInteractions {
    fun getCategories()
    fun onClickCategory(id: String, label: String)
    fun controlSearchVisibility()
    fun onChangeSearch(text: String)
    fun getSearchResult()
    fun onClickProduct(id: String)
}
