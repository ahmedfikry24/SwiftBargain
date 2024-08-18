package com.example.swiftbargain.ui.sale.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.Sale
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<SaleUiState, SaleEvents>(SaleUiState()), SaleInteractions {

    private val args = savedStateHandle.toRoute<Sale>()
    private val searchQuery = MutableStateFlow("")

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

    override fun controlSearchVisibility() {
        _state.update { it.copy(isSearchVisible = !it.isSearchVisible) }
    }

    override fun onChangeSearch(search: String) {
        _state.update { it.copy(search = search) }
        searchQuery.value = search
    }

    @OptIn(FlowPreview::class)
    fun observeSearch() {
        viewModelScope.launch {
            searchQuery.debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    searchForProduct()
                }
        }
    }

    override fun searchForProduct() {
        _state.update { it.copy(searchContentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.searchSaleProducts(args.id, state.value.search) },
            ::searchSuccess,
            ::searchError
        )
    }

    private fun searchSuccess(products: List<ProductDto>) {
        _state.update { value ->
            value.copy(
                searchContentStatus = ContentStatus.VISIBLE,
                searchProducts = products.map { it.toUiState() }
            )
        }
    }

    private fun searchError(error: BaseError) {
        _state.update { it.copy(searchContentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickProduct(id: String) {
        sendEvent(SaleEvents.GoToProduct(id))
    }
}
