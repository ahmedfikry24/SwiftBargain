package com.example.swiftbargain.ui.home.view_model

sealed interface HomeEvents {
    data class NavigateToSale(val id: String, val title: String, val url: String) : HomeEvents
    data class NavigateToCategory(val id: String) : HomeEvents
    data class NavigateToProductDetails(val id: String) : HomeEvents
    data object NavigateToFavorites : HomeEvents
}
