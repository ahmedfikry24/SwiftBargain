package com.example.swiftbargain.ui.account.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() :
    BaseViewModel<AccountUiState, AccountEvents>(AccountUiState()), AccountInteractions
