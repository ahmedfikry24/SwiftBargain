package com.example.swiftbargain.ui.offer.view_model

import com.example.swiftbargain.data.models.SaleAdDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<OfferUiState, OfferEvents>(OfferUiState()), OfferInteractions {

    init {
        getSaleAds()
    }

    override fun getSaleAds() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getSaleAds() },
            ::saleSuccess,
            { saleError() }
        )
    }

    private fun saleSuccess(sales: List<SaleAdDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                offers = sales.map { it.toUiState() }
            )

        }
    }

    private fun saleError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickAd(id: String, title: String, url: String) {
        sendEvent(OfferEvents.NavigateToSale(id, title, url))
    }
}
