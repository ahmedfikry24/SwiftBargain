package com.example.swiftbargain.ui.offer.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor() :
    BaseViewModel<OfferUiState, OfferEvents>(OfferUiState()), OfferInteractions
