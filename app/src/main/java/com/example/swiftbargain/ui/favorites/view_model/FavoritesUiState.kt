package com.example.swiftbargain.ui.favorites.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class FavoritesUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val favorites: List<ProductUiState> = listOf()
) 
