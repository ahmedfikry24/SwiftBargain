package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.SaleAdDto
import com.example.swiftbargain.data.repository.Repository
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
            { dataError() }
        )
    }

    private fun dataSuccess(data: Map<String, List<Any>>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                saleAds = (data[SALE_ADS] as List<*>).map { (it as SaleAdDto).toUiState() },

                categories = (data[CATEGORIES] as List<*>).map { (it as CategoryDto).toUiState() },

                flashSale = (data[PRODUCTS] as List<*>)
                    .filter {
                        ((it as ProductDto).discountPercentage.toDoubleOrNull() ?: 1.0) >= 50.0
                    }
                    .map { (it as ProductDto).toUiState() }.take(5),

                megaSale = (data[PRODUCTS] as List<*>)
                    .filter {
                        ((it as ProductDto).discountPercentage.toDoubleOrNull() ?: 1.0) < 50.0
                    }
                    .map { (it as ProductDto).toUiState() }.take(5),

                recommendedProducts = (data[PRODUCTS] as List<*>)
                    .filterNot { (it as ProductDto).recommended }
                    .map { (it as ProductDto).toUiState() }.take(8)
            )
        }
    }

    private fun dataError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickSale(id: String, title: String, url: String) {
        sendEvent(HomeEvents.NavigateToSale(id, title, url))
    }

    override fun onClickCategory(id: String, label: String) {
        sendEvent(HomeEvents.NavigateToCategory(id, label))
    }

    override fun onClickProduct(id: String) {
        sendEvent(HomeEvents.NavigateToProductDetails(id))
    }

    override fun onClickFavorites() {
        sendEvent(HomeEvents.NavigateToFavorites)
    }

    override fun onClickNotifications() {
        sendEvent(HomeEvents.NavigateToNotifications)
    }

    companion object {
        const val SALE_ADS = "saleAd"
        const val CATEGORIES = "categories"
        const val PRODUCTS = "products"
    }
}
