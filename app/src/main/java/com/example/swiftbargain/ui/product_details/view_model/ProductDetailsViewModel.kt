package com.example.swiftbargain.ui.product_details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.ProductDetails
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<ProductDetailsUiState, ProductDetailsEvents>(ProductDetailsUiState()),
    ProductDetailsInteractions {
    val args = savedStateHandle.toRoute<ProductDetails>()

    init {
        getData()
    }

    override fun getData() {
        getDetails()
    }

    private fun getDetails() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getProductDetails(args.id) },
            ::detailsSuccess,
            ::detailsError
        )
    }

    private fun detailsSuccess(product: ProductDto) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                product = product.toUiState()
            )
        }
    }

    private fun detailsError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickBack() {
        sendEvent(ProductDetailsEvents.NavigateToBack)
    }

    override fun onClickFavorite() {

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

    }
}