package com.example.swiftbargain.ui.login.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()), LoginInteractions {

}
