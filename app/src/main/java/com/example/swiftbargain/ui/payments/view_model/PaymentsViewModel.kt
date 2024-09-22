package com.example.swiftbargain.ui.payments.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor() :
    BaseViewModel<PaymentsUiState, PaymentsEvents>(PaymentsUiState()), PaymentsInteractions
