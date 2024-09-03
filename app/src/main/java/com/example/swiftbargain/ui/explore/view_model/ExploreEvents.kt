package com.example.swiftbargain.ui.explore.view_model

sealed interface ExploreEvents {
    data class NavigateToCategory(val id: String, val label: String) : ExploreEvents
    data class NavigateToProductDetails(val id: String) : ExploreEvents
}
