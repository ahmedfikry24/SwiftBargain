package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartCheckOutViewModel @Inject constructor() :
    BaseViewModel<CartCheckOutUiState, CartCheckOutEvents>(CartCheckOutUiState()),
    CartCheckOutInteractions
