package com.example.swiftbargain.ui.order_details.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.OrderUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

data class OrderDetailsUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val order: OrderUiState = OrderUiState(),
    val products: List<ProductUiState> = listOf()
) 
