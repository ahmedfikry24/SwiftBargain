package com.example.swiftbargain.ui.sale.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class SaleUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val title: String = "",
    val bannerUrl: String = "",
    val bannerTitle: String = "",
    val products: List<ProductUiState> = listOf(),
    val pageNumber: Int = 1,
    val isLoadMoreProducts: Boolean = false,
    val isSearchVisible: Boolean = false,
    val search: String = "",
    val isSearchError: Boolean = false,
    val searchProducts: List<ProductUiState> = listOf()
)
