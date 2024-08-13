package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.SaleAdUiState

data class HomeUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val search: String = "",
    val saleAds: List<SaleAdUiState> = listOf(),
    val categories: List<CategoryUiSate> = listOf(),
    val flashSale: List<ProductUiState> = listOf(),
    val megaSale: List<ProductUiState> = listOf(),
    val recommendedProducts: List<ProductUiState> = listOf()
)
