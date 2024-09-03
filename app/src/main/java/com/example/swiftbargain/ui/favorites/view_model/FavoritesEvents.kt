package com.example.swiftbargain.ui.favorites.view_model

sealed interface FavoritesEvents {
    data object NavigateToBack : FavoritesEvents
    data class NavigateToProductDetails(val id: String) : FavoritesEvents
}
