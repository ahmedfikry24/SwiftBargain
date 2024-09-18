package com.example.swiftbargain.ui.account.view_model

sealed interface AccountEvents {
    data object NavigateToProfile : AccountEvents
    data object NavigateToOrders : AccountEvents
    data object NavigateToAddress : AccountEvents
    data object NavigateToPayment : AccountEvents
}
