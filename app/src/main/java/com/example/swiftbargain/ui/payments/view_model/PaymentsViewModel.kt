package com.example.swiftbargain.ui.payments.view_model

import com.example.swiftbargain.data.local.room.entity.CreditEntity
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<PaymentsUiState, PaymentsEvents>(PaymentsUiState()), PaymentsInteractions {
    init {
        getAllCredits()
    }

    override fun onClickBack() {

    }

    override fun getAllCredits() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getAllCreditCards() },
            ::creditsSuccess,
            { creditsError() }
        )
    }

    private fun creditsSuccess(credits: List<CreditEntity>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allCredits = credits.map { it.toUiState() }
            )
        }
    }

    private fun creditsError() {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun controlAddCreditVisibility() {
        _state.update { it.copy(isAddCreditVisible = !it.isAddCreditVisible) }
    }
}
