package com.example.swiftbargain.ui.category.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.Category
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<CategoryUiState, CategoryEvents>(CategoryUiState()), CategoryInteractions {
    private val args = savedStateHandle.toRoute<Category>()

    init {
        getProducts()
    }


    override fun getProducts() {
        tryExecute(
            { repository.getCategoryProducts(args.id, null) },
            ::productsSuccess,
            { productsError() }
        )
    }

    private fun productsSuccess(products: List<ProductDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                title = args.label,
                products = products.map { it.toUiState() }
            )
        }
    }

    override fun getMoreProducts(id: String?) {
        _state.update { it.copy(isLoadMoreProducts = true) }
        tryExecute(
            { repository.getSaleProducts(args.id, id) },
            ::moreProductsSuccess,
            { productsError() }
        )
    }

    private fun moreProductsSuccess(products: List<ProductDto>) {
        _state.update { value ->
            value.copy(
                isLoadMoreProducts = false,
                products = value.products.toMutableList().apply {
                    addAll(products.map { it.toUiState() })
                },
                pageNumber = value.pageNumber.inc(),
            )
        }
    }

    private fun productsError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE, isLoadMoreProducts = false) }
    }

    override fun onCLickBack() {
        sendEvent(CategoryEvents.NavigateToBack)
    }

    override fun onClickProduct(id: String) {
        sendEvent(CategoryEvents.NavigateToProductDetails(id))
    }

}
