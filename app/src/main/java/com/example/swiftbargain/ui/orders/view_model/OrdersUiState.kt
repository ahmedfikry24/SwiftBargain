package com.example.swiftbargain.ui.orders.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.OrderUiState

data class OrdersUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val orders: List<OrderUiState> = listOf()
) 
