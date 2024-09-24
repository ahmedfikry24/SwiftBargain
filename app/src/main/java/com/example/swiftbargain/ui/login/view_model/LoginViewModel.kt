package com.example.swiftbargain.ui.login.view_model

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.base.EmailIsNoVerified
import com.example.swiftbargain.ui.base.NoInternetConnection
import com.example.swiftbargain.ui.base.SomethingWentWrong
import com.example.swiftbargain.ui.base.UserNotFound
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.validateEmail
import com.example.swiftbargain.ui.utils.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()), LoginInteractions {

    override fun onChangeEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    override fun onChangePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    override fun loginWithEmailAndPassword() {
        if (validateFields()) {
            _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
            tryExecute(
                { repository.loginWithEmailAndPassword(state.value.email, state.value.password) },
                ::signInSuccess,
                ::signInError
            )
        }
    }

    private fun signInSuccess(success: String) {
        viewModelScope.launch {
            repository.setUserUid(success)
        }
        sendEvent(LoginEvents.LoginSuccess)
    }

    private fun signInError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is UserNotFound -> sendEvent(LoginEvents.InvalidEmailOrPassword)
            is EmailIsNoVerified -> controlUnVerifiedEmailDialogVisibility()
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    private fun validateFields(): Boolean {
        val validateEmail = state.value.email.validateEmail()
        val validatePassword = state.value.password.validatePassword()
        val hasError = listOf(validateEmail, validatePassword).contains(false)
        _state.update {
            it.copy(
                emailError = !validateEmail,
                passwordError = !validatePassword
            )
        }
        return !hasError
    }

    override fun controlUnVerifiedEmailDialogVisibility() {
        _state.update { it.copy(unVerifiedEmailDialog = !it.unVerifiedEmailDialog) }
    }

    override fun getGoogleCredential(intent: Intent) {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.signWithGoogleIntent(intent) },
            ::googleCredentialSuccess,
            ::googleCredentialError
        )
    }

    private fun googleCredentialSuccess(token: String) {
        loginWithGoogleId(token)
    }

    private fun googleCredentialError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is UserNotFound -> sendEvent(LoginEvents.CredentialFailed)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    private fun loginWithGoogleId(id: String) {
        tryExecute(
            { repository.loginWithGoogle(id) },
            ::googleAuthSuccess,
            ::googleAuthError
        )

    }

    private fun googleAuthSuccess(id: String) {
        viewModelScope.launch {
            repository.setUserUid(id)
        }
        sendEvent(LoginEvents.LoginSuccess)
    }

    private fun googleAuthError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is UserNotFound -> sendEvent(LoginEvents.CredentialFailed)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    override fun loginWithFaceBook(id: String) {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.loginWithFacebook(id) },
            ::facebookAuthSuccess,
            ::facebookAuthError
        )
    }

    private fun facebookAuthSuccess(id: String) {
        viewModelScope.launch {
            repository.setUserUid(id)
            sendEvent(LoginEvents.LoginSuccess)
        }
    }

    private fun facebookAuthError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is UserNotFound -> sendEvent(LoginEvents.CredentialFailed)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    override fun controlResetPasswordBottomSheetVisibility() {
        _state.update { it.copy(resetPasswordBottomSheet = !it.resetPasswordBottomSheet) }
    }

    override fun onChangeForgetPasswordEmail(email: String) {
        _state.update { it.copy(forgetPasswordEmail = email) }
    }

    override fun onSendResetPasswordEmail() {
        val isEmailValid = state.value.forgetPasswordEmail.validateEmail()
        _state.update { it.copy(forgetPasswordEmailError = !isEmailValid) }
        if (isEmailValid) {
            tryExecute(
                { repository.resetPassword(state.value.forgetPasswordEmail) },
                { resetPasswordSuccess() },
                ::resetPasswordError
            )
        }
    }

    private fun resetPasswordSuccess() {
        controlResetPasswordDialogVisibility()
    }

    private fun resetPasswordError(error: BaseError) {
        when (error) {
            is UserNotFound -> {
                controlResetPasswordBottomSheetVisibility()
                sendEvent(LoginEvents.EmailNotRegister)
            }

            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is SomethingWentWrong -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    override fun controlResetPasswordDialogVisibility() {
        _state.update { it.copy(resetPasswordDialog = !it.resetPasswordDialog) }
    }

    override fun onRegister() {
        sendEvent(LoginEvents.NavToRegister)
    }

}
