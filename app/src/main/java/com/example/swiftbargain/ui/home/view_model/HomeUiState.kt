package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.ui.utils.ContentStatus

data class HomeUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val search: String = "",
    val carousalItems: List<CarousalItemInfo> = listOf()
) {
    data class CarousalItemInfo(
        val id: String,
        val title: String,
        val url: String
    )
}
