package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.SaleAdDto

data class SaleAdUiState(
    val id: String,
    val title: String,
    val type: String,
    val url: String
)

fun SaleAdDto.toUiState(): SaleAdUiState {
    return SaleAdUiState(
        id = this.id,
        title = this.title,
        type = this.type,
        url = this.url
    )
}
