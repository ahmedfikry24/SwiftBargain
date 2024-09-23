package com.example.swiftbargain.ui.account.view_model

import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<AccountUiState, AccountEvents>(AccountUiState()), AccountInteractions {
    override fun onClickProfile() {
        sendEvent(AccountEvents.NavigateToProfile)
    }

    override fun onClickOrder() {
        sendEvent(AccountEvents.NavigateToOrders)
    }

    override fun onClickAddress() {
        sendEvent(AccountEvents.NavigateToAddress)
    }

    override fun onClickPayment() {
        sendEvent(AccountEvents.NavigateToPayment)
    }

    override fun onClickLogOut() {
        viewModelScope.launch {
            repository.setUserUid("")
            repository.deleteAllCreditCards()
            repository.deleteAllCartProducts()
            sendEvent(AccountEvents.LogOut)
        }
    }

}
