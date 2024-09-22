package com.example.swiftbargain.ui.payments.view_model

import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.local.room.entity.CreditEntity
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.toEntity
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    // region add credit
    override fun onChangeCreditNumber(number: String) {
        _state.update { it.copy(addCredit = it.addCredit.copy(cardNum = number)) }
    }

    override fun onChangeExpiration(number: String) {
        _state.update { it.copy(addCredit = it.addCredit.copy(expiration = number)) }
    }

    override fun onChangeSecurityNumber(number: String) {
        _state.update { it.copy(addCredit = it.addCredit.copy(securityCode = number)) }
    }

    override fun onChangeHolderNMame(name: String) {
        _state.update { it.copy(addCredit = it.addCredit.copy(holderName = name)) }
    }

    override fun addCard() {
        if (validateAddCredit()) {
            viewModelScope.launch {
                repository.addCreditCard(state.value.addCredit.toEntity())
                _state.update {
                    it.copy(
                        isAddCreditVisible = false,
                        addCredit = CreditUiSate(),
                        allCredits = it.allCredits.toMutableList()
                            .apply { add(it.addCredit) }
                    )
                }
            }
        }
    }

    private fun validateAddCredit(): Boolean {
        val value = state.value.addCredit
        val cardNum = value.cardNum.validateRequireFields() && value.cardNum.length == 16
        val expiration = value.expiration.validateRequireFields()
        val securityCode = value.securityCode.validateRequireFields()
        val holderName = value.holderName.validateRequireFields()

        val hasError = listOf(cardNum, expiration, securityCode, holderName).any { !it }
        _state.update {
            it.copy(
                addCredit = it.addCredit.copy(
                    cardNumError = !cardNum,
                    expirationError = !expiration,
                    securityCodeError = !securityCode,
                    holderNameError = !holderName
                )
            )
        }

        return !hasError
    }
    // endregion
}
