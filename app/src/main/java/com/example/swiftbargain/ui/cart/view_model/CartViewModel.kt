package com.example.swiftbargain.ui.cart.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.swiftbargain.data.local.room.entity.CartProductEntity
import com.example.swiftbargain.data.models.CouponCodeDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.Cart
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CartProductUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.toEntity
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<CartUiState, CartEvents>(CartUiState()), CartInteractions {
    private val args = savedStateHandle.toRoute<Cart>()

    init {
        getDate()
    }


    override fun getDate() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            {
                mapOf(
                    PRODUCTS to repository.getAllCartProducts(),
                    COUPONS to repository.getAllCouponCodes()
                )
            },
            ::dataSuccess,
            { dataError() }
        )
    }

    private fun dataSuccess(data: Map<String, Any>) {
        val products = (data[PRODUCTS] as List<*>).map { (it as CartProductEntity).toUiState() }
        val totalPrice = calculateTotalPrice(products)
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                products = products,
                coupons = (data[COUPONS] as List<*>).map { (it as CouponCodeDto).toUiState() },
                productsPrice = totalPrice,
                totalPrice = totalPrice
            )
        }
    }

    private fun dataError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
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
        sendEvent(CartEvents.NavigateToProductDetails(id))
    }

    override fun onChangeQuantity(index: Int, quantity: Int) {
        val coupon = state.value.coupons.find { it.code == state.value.couponCode }
        _state.update { value ->
            value.copy(
                products = value.products.toMutableList().apply {
                    this[index] = value.products[index].copy(orderQuantity = quantity)
                }
            )
        }
        _state.update {
            it.copy(
                productsPrice = calculateTotalPrice(it.products),
                totalPrice = calculateTotalPrice(it.products, coupon?.discount?.toInt())
            )
        }
        viewModelScope.launch { repository.addProductToCart(state.value.products[index].toEntity()) }
    }

    override fun onChangeCouponCode(code: String) {
        _state.update { it.copy(couponCode = code) }
    }

    override fun checkCouponCode() {
        val coupon = state.value.coupons.find { it.code == state.value.couponCode }
        _state.update { value ->
            value.copy(
                couponCodeError = !value.coupons.any { it.code == value.couponCode },
                couponDiscount = coupon?.discount ?: "",
                totalPrice = calculateTotalPrice(state.value.products, coupon?.discount?.toInt())
            )
        }
    }

    override fun onClickCheckOut() {
        sendEvent(CartEvents.NavigateToCartCheckOut)
    }

    override fun checkClearCart() {
        if (args.isCartCleared == true) {
            viewModelScope.launch { repository.deleteAllCartProducts() }
            _state.update { it.copy(products = listOf()) }
        }
    }

    companion object {
        private const val PRODUCTS = "products"
        private const val COUPONS = "coupons"
    }
}


private fun calculateTotalPrice(products: List<CartProductUiState>, discount: Int? = null): Int {
    var totalPrice = 0
    products.forEach { totalPrice += it.price.toInt() * it.orderQuantity }
    if (discount != null)
        totalPrice -= discount
    return totalPrice
}