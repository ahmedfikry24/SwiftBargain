package com.example.swiftbargain.ui.offer.view_model

sealed interface OfferEvents {
    data class NavigateToSale(val id: String, val title: String, val url: String) : OfferEvents
}
