package com.example.swiftbargain.ui.orders.view_model

import com.example.swiftbargain.data.models.OrderDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.base.UserNotFound
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel<OrdersUiState, OrdersEvents>(OrdersUiState()), OrdersInteractions {

    init {
        getAllOrders()
    }

    override fun onClickBack() {
        sendEvent(OrdersEvents.NavigateToBack)
    }

    override fun getAllOrders() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getAllOrders() },
            ::ordersSuccess,
            ::ordersError
        )
    }

    private fun ordersSuccess(orders: List<OrderDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                orders = orders.map { it.toUiState() }
            )
        }
    }

    private fun ordersError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
        if (error is UserNotFound) sendEvent(OrdersEvents.UnAuthorizedAccess)
    }

    override fun onClickOrder(id: String) {
        sendEvent(OrdersEvents.NavigateToOrderDetails(id))
    }


}
