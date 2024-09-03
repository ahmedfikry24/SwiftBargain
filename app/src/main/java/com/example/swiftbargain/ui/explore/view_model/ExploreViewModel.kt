package com.example.swiftbargain.ui.explore.view_model

import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
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
class ExploreViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<ExploreUiState, ExploreEvents>(ExploreUiState()), ExploreInteractions {

    private val searchQuery = MutableStateFlow("")

    init {
        getCategories()
        observeSearch()
    }

    override fun getCategories() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getAllCategories() },
            ::categoriesSuccess,
            { categoriesError() }
        )
    }


    private fun categoriesSuccess(categories: List<CategoryDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                manCategories = categories.filter { it.label.contains("Man") }
                    .map { it.toUiState() },
                womanCategories = categories.filterNot { it.label.contains("Man") }
                    .map { it.toUiState() }
            )
        }
    }


    private fun categoriesError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickCategory(id: String, label: String) {
        sendEvent(ExploreEvents.NavigateToCategory(id, label))
    }

    override fun controlSearchVisibility() {
        _state.update { it.copy(isSearchVisible = !it.isSearchVisible) }
    }

    override fun onChangeSearch(text: String) {
        _state.update { it.copy(searchValue = text) }
        searchQuery.value = text
    }

    @OptIn(FlowPreview::class)
    fun observeSearch() {
        viewModelScope.launch {
            searchQuery.debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { getSearchResult() }
        }
    }

    override fun getSearchResult() {
        _state.update { it.copy(searchContentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.searchProducts(state.value.searchValue) },
            ::searchSuccess,
            { searchError() }
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

    private fun searchError() {
        _state.update { it.copy(searchContentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickProduct(id: String) {
        sendEvent(ExploreEvents.NavigateToProductDetails(id))
    }

}
