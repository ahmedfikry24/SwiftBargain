package com.example.swiftbargain.ui.category.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class CategoryUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val title: String = "",
    val products: List<ProductUiState> = listOf(),
    val isLoadMoreProducts: Boolean = false,
    val pageNumber: Int = 1
) 
