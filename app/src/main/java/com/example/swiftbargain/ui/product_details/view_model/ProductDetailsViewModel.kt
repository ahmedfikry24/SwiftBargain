package com.example.swiftbargain.ui.product_details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.ReviewDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.ProductDetails
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

    companion object {
        private const val DETAILS = "details"
        private const val IS_FAVORITE = "isFavorite"
        private const val REVIEWS = "reviews"
    }
}