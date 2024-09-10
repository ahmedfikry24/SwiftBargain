package com.example.swiftbargain.ui.cart_check_out.view_model

import androidx.lifecycle.SavedStateHandle
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartCheckOutViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<CartCheckOutUiState, CartCheckOutEvents>(CartCheckOutUiState()),
    CartCheckOutInteractions {

    override fun controlAddAddressVisibility() {
        _state.update { it.copy(isAddAddressVisible = !it.isAddAddressVisible) }
    }

}
