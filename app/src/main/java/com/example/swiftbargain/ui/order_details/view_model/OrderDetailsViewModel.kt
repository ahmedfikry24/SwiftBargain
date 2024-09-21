package com.example.swiftbargain.ui.order_details.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor() :
    BaseViewModel<OrderDetailsUiState, OrderDetailsEvents>(OrderDetailsUiState()),
    OrderDetailsInteractions
