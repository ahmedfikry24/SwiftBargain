package com.example.swiftbargain.ui.explore.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class ExploreUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val isSearchVisible: Boolean = false,
    val searchContentStatus: ContentStatus = ContentStatus.VISIBLE,
    val searchValue: String = "",
    val searchProducts: List<ProductUiState> = listOf(),
    val manCategories: List<CategoryUiSate> = listOf(),
    val womanCategories: List<CategoryUiSate> = listOf(),
)
