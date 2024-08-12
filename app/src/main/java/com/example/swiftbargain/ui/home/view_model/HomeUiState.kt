package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.SaleAdDto
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.SaleAdUiState

data class HomeUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val search: String = "",
    val saleAds: List<SaleAdUiState> = listOf(),
    val categories: List<CategoryUiSate> = listOf()
)


fun SaleAdDto.toUiState(): SaleAdUiState {
    return SaleAdUiState(
        id = this.id,
        title = this.title,
        url = this.url
    )
}

fun CategoryDto.toUiState(): CategoryUiSate {
    return CategoryUiSate(
        id = this.id,
        label = this.label,
        url = this.url
    )
}

