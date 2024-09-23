package com.example.swiftbargain.ui.product_details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.swiftbargain.data.local.room.entity.CartProductEntity
import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.ReviewDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toFavoriteEntity
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<ProductDetailsUiState, ProductDetailsEvents>(ProductDetailsUiState()),
    ProductDetailsInteractions {
    private val args = savedStateHandle.toRoute<ProductDetails>()

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            {
                mapOf(
                    DETAILS to repository.getProductDetails(args.id),
                    IS_FAVORITE to repository.getAllFavorites(),
                    REVIEWS to repository.getProductReviews(args.id)
                )
            },
            ::dataSuccess,
            { dataError() }
        )
    }

    private fun dataSuccess(info: Map<String, Any>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                product = (info[DETAILS] as ProductDto).toUiState(),
                isFavorite = (info[IS_FAVORITE] as List<*>).any { (it as FavoriteProductEntity).id == (info[DETAILS] as ProductDto).id },
                reviews = (info[REVIEWS] as List<*>).map { (it as ReviewDto).toUiState() }
            )
        }
    }

    private fun dataError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickBack() {
        sendEvent(ProductDetailsEvents.NavigateToBack)
    }

    override fun onClickFavorite() {
        val value = state.value
        viewModelScope.launch {
            if (state.value.isFavorite) {
                repository.removeFavoriteProduct(value.product.id)
            } else {
                repository.addFavoriteProduct(value.product.toFavoriteEntity())
            }
        }
        _state.update { it.copy(isFavorite = !it.isFavorite) }
    }

    override fun onClickSize(size: String) {
        _state.update { it.copy(selectedSize = size) }
    }

    override fun onCLickColor(color: Long) {
        _state.update { it.copy(selectedColor = color) }
    }

    override fun onCLickMoreReviews() {
        sendEvent(ProductDetailsEvents.NavigateToReviews)
    }

    override fun onCLickAddToCart() {
        val value = state.value.product
        if (value.sizes.isNotEmpty() && value.colors.isNotEmpty()) {

            if (validateFullProductInfo()) addToCart()

        } else addToCart()
    }

    private fun addToCart() {
        val value = state.value.product
        viewModelScope.launch {
            repository.addProductToCart(
                CartProductEntity(
                    id = value.id,
                    name = value.title,
                    price = value.priceAfterDiscount,
                    imageUrl = value.url.first(),
                    color = state.value.selectedColor.toString(),
                    size = state.value.selectedSize,
                    quantity = value.quantity
                )
            )
            sendEvent(ProductDetailsEvents.AddToCartSuccessfully)
        }
    }

    private fun validateFullProductInfo(): Boolean {
        val value = state.value
        val validateSize = value.selectedSize.validateRequireFields()
        val validateColor = value.selectedColor != 0L

        if (!validateSize || !validateColor) {
            sendEvent(ProductDetailsEvents.CompleteProductInfo)
            return false
        }
        return true
    }

    companion object {
        private const val DETAILS = "details"
        private const val IS_FAVORITE = "isFavorite"
        private const val REVIEWS = "reviews"
    }
}