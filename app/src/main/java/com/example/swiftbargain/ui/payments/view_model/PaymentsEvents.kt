package com.example.swiftbargain.ui.payments.view_model

sealed interface PaymentsEvents {
    data object NavigateToBack : PaymentsEvents
    data object AddCreditSuccess : PaymentsEvents
}
