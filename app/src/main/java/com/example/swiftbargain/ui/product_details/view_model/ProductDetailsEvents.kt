package com.example.swiftbargain.ui.product_details.view_model

sealed interface ProductDetailsEvents {
    data object NavigateToBack : ProductDetailsEvents
    data object NavigateToReviews : ProductDetailsEvents
    data object AddToCartSuccessfully : ProductDetailsEvents
}
