package com.example.swiftbargain.ui.home.view_model

sealed interface HomeEvents {
    data class GoToSale(val id: String, val title: String) : HomeEvents
    data class GoToCategory(val id: String) : HomeEvents
    data class GoToProductDetails(val id: String) : HomeEvents
}
