package com.example.swiftbargain.ui.sale.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.Sale
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<SaleUiState, SaleEvents>(SaleUiState()), SaleInteractions {

    private val args = savedStateHandle.toRoute<Sale>()

    override fun getProducts() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getSaleProducts(args.id, null) },
            ::productsSuccess,
            ::productsError
        )
    }

    private fun productsSuccess(items: List<ProductDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                products = items.map { it.toUiState() },
                title = "${args.title} Sale",
                bannerTitle = args.title,
                bannerUrl = args.url
            )
        }
    }


    override fun getMoreProducts(lastItemId: String?) {
        _state.update { it.copy(isLoadMoreProducts = true) }
        tryExecute(
            { repository.getSaleProducts(args.id, lastItemId) },
            ::moreProductsSuccess,
            ::productsError
        )
    }

    private fun moreProductsSuccess(items: List<ProductDto>) {
        _state.update { value ->
            value.copy(
                isLoadMoreProducts = false,
                products = value.products.toMutableList().apply {
                    addAll(items.map { it.toUiState() })
                },
                pageNumber = value.pageNumber.inc(),
            )
        }
    }

    private fun productsError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickBack() {
        sendEvent(SaleEvents.GoBack)
    }

    override fun onClickSearch() {
        sendEvent(SaleEvents.GoToSearch)
    }

    override fun onClickProduct(id: String) {
        sendEvent(SaleEvents.GoToProduct(id))
    }
}
