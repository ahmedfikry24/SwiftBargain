package com.example.swiftbargain.ui.category.view_model

sealed interface CategoryEvents {
    data object NavigateToBack : CategoryEvents
    data class NavigateToProductDetails(val id: String) : CategoryEvents
}
