package com.example.swiftbargain.ui.login.view_model

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.data.utils.SignInResult
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.base.NoInternetConnection
import com.example.swiftbargain.ui.base.UserNotFound
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.validateEmail
import com.example.swiftbargain.ui.utils.validateRequireFields
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
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        if (validateFields()) {
            tryExecute(
                { repository.loginEmailAndPassword(state.value.email, state.value.password) },
                ::signInSuccess,
                ::signInError
            )
        }
    }

    private fun signInSuccess(success: String) {
        val job = viewModelScope.launch {
            repository.setUserUid(success)
            repository.setIsLogin(true)
        }
        if (job.isCompleted)
            sendEvent(LoginEvents.LoginSuccess)
    }

    private fun signInError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is UserNotFound -> sendEvent(LoginEvents.InvalidEmailOrPassword)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    private fun validateFields(): Boolean {
        val validateEmail = state.value.email.validateEmail()
        val validatePassword = state.value.password.validateRequireFields()
        val hasError = listOf(validateEmail, validatePassword).contains(false)
        _state.update {
            it.copy(
                emailError = !validateEmail,
                passwordError = !validatePassword
            )
        }
        return !hasError
    }

    override fun getGoogleCredential(intent: Intent) {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.loginWithGoogleIntent(intent) },
            ::googleCredentialSuccess,
            ::googleCredentialError
        )
    }

    private fun googleCredentialSuccess(success: SignInResult) {
        loginWithGoogleId(success.id)
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
        val job = viewModelScope.launch {
            repository.setUserUid(id)
            repository.setIsLogin(true)
        }
        if (job.isCompleted)
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
            { repository.signInWithFacebook(id) },
            ::facebookAuthSuccess,
            ::facebookAuthError
        )
    }

    private fun facebookAuthSuccess(id: String) {
        val job = viewModelScope.launch {
            repository.setUserUid(id)
            repository.setIsLogin(true)
        }
        if (job.isCompleted)
            sendEvent(LoginEvents.LoginSuccess)
    }

    private fun facebookAuthError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is NoInternetConnection -> sendEvent(LoginEvents.NoInternetConnection)
            is UserNotFound -> sendEvent(LoginEvents.CredentialFailed)
            else -> sendEvent(LoginEvents.SomeThingWentWrong)
        }
    }

    override fun onForgetPassword() {

    }

    override fun onRegister() {

    }

}
