package com.example.swiftbargain.ui.cart.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : BaseViewModel<CartUiState, CartEvents>(CartUiState()),
    CartInteractions
