package com.example.swiftbargain.ui.login.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()), LoginInteractions {

    override fun onChangeEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    override fun onChangePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    override fun onClickSignIn() {

    }

    override fun loginWithGoogle() {

    }

    override fun loginWithFaceBook() {

    }

    override fun onForgetPassword() {

    }

    override fun onRegister() {

    }

}
