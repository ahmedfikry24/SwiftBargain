package com.example.swiftbargain.ui.register.viiew_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() :
    BaseViewModel<RegisterUiState, RegisterEvents>(RegisterUiState()), RegisterInteractions {
    override fun onChangeName(name: String) {

    }

    override fun onChangeEmail(email: String) {

    }

    override fun onChangePassword(password: String) {

    }

    override fun onChangePasswordAgain(password: String) {

    }

    override fun registerWithEmailAndPassword() {

    }

    override fun registerWithGoogle() {

    }

    override fun registerWithFacebook() {

    }

    override fun onSignIn() {

    }
}
