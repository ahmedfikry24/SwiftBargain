package com.example.swiftbargain.ui.payments.view_model

import com.example.swiftbargain.ui.utils.shred_interactions.AddCreditInteractions

interface PaymentsInteractions : AddCreditInteractions {
    fun onClickBack()
    fun getAllCredits()
    fun controlAddCreditVisibility()
}
