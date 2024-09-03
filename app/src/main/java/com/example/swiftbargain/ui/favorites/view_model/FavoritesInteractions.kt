package com.example.swiftbargain.ui.favorites.view_model

interface FavoritesInteractions {
    fun getFavorites()
    fun onClickBack()
    fun onClickProduct(id: String)
    fun onRemoveFavorite(id: String)
}
