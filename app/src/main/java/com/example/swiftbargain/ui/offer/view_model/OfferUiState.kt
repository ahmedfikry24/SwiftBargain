package com.example.swiftbargain.ui.offer.view_model

import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.SaleAdUiState

data class OfferUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val offers: List<SaleAdUiState> = listOf()
) 
