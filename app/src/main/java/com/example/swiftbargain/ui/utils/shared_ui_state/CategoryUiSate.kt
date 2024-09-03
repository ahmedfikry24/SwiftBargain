package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.CategoryDto

data class CategoryUiSate(
    val id: String,
    val label: String,
    val url: String
)

fun CategoryDto.toUiState(): CategoryUiSate {
    return CategoryUiSate(
        id = this.id,
        label = this.label,
        url = this.url
    )
}
