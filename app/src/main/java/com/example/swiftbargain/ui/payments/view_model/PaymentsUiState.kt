package com.example.swiftbargain.ui.payments.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate

data class PaymentsUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val allCredits: List<CreditUiSate> = listOf(),
    val isAddCreditVisible: Boolean = false,
    val addCredit: CreditUiSate = CreditUiSate()
) 
