package com.example.swiftbargain.ui.cart.view_model

import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toEntity
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<CartUiState, CartEvents>(CartUiState()), CartInteractions {

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            val result = repository.getAllCartProducts()
            _state.update { value ->
                value.copy(
                    contentStatus = ContentStatus.VISIBLE,
                    products = result.map { it.toUiState() }
                )
            }
        }
    }

    override fun onRemoveItem(id: String) {
        viewModelScope.launch {
            repository.removeProductFromCart(id)
            _state.update { value ->
                value.copy(products = value.products.filterNot { it.id == id })
            }
        }
    }

    override fun onClickItem(id: String) {

    }

    override fun onChangeQuantity(index: Int, quantity: Int) {
        _state.update { value ->
            value.copy(
                products = value.products.toMutableList().apply {
                    this[index] = value.products[index].copy(orderQuantity = quantity)
                }
            )
        }
        viewModelScope.launch { repository.addProductToCart(state.value.products[index].toEntity()) }
    }
}
