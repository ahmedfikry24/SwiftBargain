package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.SaleAdDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()), HomeInteractions {

    override fun getData() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            {
                mapOf(
                    SALE_ADS to repository.getSaleAds(),
                    CATEGORIES to repository.getAllCategories(),
                    PRODUCTS to repository.getAllProducts()
                )
            },
            ::dataSuccess,
            ::dataError
        )
    }

    private fun dataSuccess(data: Map<String, List<Any>>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                saleAds = (data[SALE_ADS] as List<SaleAdDto>).map { it.toUiState() },

                categories = (data[CATEGORIES] as List<CategoryDto>).map { it.toUiState() },

                flashSale = (data[PRODUCTS] as List<ProductDto>)
                    .filter {
                        (it.discountPercentage.toDoubleOrNull() ?: 1.0) >= 50.0
                    }
                    .map { it.toUiState() }.take(5),

                megaSale = (data[PRODUCTS] as List<ProductDto>)
                    .filter {
                        (it.discountPercentage.toDoubleOrNull() ?: 1.0) < 50.0
                    }
                    .map { it.toUiState() }.take(5),

                recommendedProducts = (data[PRODUCTS] as List<ProductDto>)
                    .filterNot { it.recommended }
                    .map { it.toUiState() }
            )
        }
    }

    private fun dataError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickSale(id: String, title: String) {
        sendEvent(HomeEvents.GoToSale(id, title))
    }

    override fun onClickCategory(id: String) {
        sendEvent(HomeEvents.GoToCategory(id))
    }

    override fun onClickProduct(id: String) {
        sendEvent(HomeEvents.GoToProductDetails(id))
    }

    companion object {
        const val SALE_ADS = "saleAd"
        const val CATEGORIES = "categories"
        const val PRODUCTS = "products"
    }
}
