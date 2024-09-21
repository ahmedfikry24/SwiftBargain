package com.example.swiftbargain.ui.orders.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor() :
    BaseViewModel<OrdersUiState, OrdersEvents>(OrdersUiState()), OrdersInteractions
