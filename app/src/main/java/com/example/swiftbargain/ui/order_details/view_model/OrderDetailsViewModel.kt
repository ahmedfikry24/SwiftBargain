package com.example.swiftbargain.ui.order_details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.OrderDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.OrderDetails
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : BaseViewModel<OrderDetailsUiState, OrderDetailsEvents>(OrderDetailsUiState()),
    OrderDetailsInteractions {
    private val args = savedStateHandle.toRoute<OrderDetails>()

    init {
        getOrderDetails()
    }

    override fun onClickBack() {

    }

    override fun getOrderDetails() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getOrderDetails(args.id) },
            ::detailsSuccess,
            ::detailsError
        )
    }

    private fun detailsSuccess(data: Pair<OrderDto, List<ProductDto>>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                order = data.first.toUiState(),
                products = data.second.map { it.toUiState() }
            )
        }
    }

    private fun detailsError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickProduct(id: String) {

    }
}
