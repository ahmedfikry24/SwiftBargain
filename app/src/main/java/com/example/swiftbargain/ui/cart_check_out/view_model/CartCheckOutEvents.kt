package com.example.swiftbargain.ui.cart_check_out.view_model

sealed interface CartCheckOutEvents {
    data object NavigateToBack : CartCheckOutEvents
    data object UnAuthorizedToAccess : CartCheckOutEvents
    data object AddCreditSuccess : CartCheckOutEvents
}
