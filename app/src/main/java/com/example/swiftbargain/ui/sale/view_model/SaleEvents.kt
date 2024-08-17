package com.example.swiftbargain.ui.sale.view_model

sealed interface SaleEvents {
    data object GoBack : SaleEvents
    data object GoToSearch : SaleEvents
    data class GoToProduct(val id: String) : SaleEvents
}
