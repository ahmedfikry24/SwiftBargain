package com.example.swiftbargain.ui.register.viiew_model

import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.base.EmailIsAlreadyUsed
import com.example.swiftbargain.ui.base.NoInternetConnection
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.validateEmail
import com.example.swiftbargain.ui.utils.validatePassword
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<RegisterUiState, RegisterEvents>(RegisterUiState()), RegisterInteractions {
    override fun onChangeName(name: String) {
        _state.update { it.copy(name = name) }
    }

    override fun onChangeEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    override fun onChangePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    override fun onChangePasswordAgain(password: String) {
        _state.update { it.copy(passwordAgain = password) }
    }

    override fun registerWithEmailAndPassword() {
        if (validateFields()) {
            _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
            val values = state.value
            tryExecute(
                {
                    repository.registerWithEmailAndPassword(
                        values.name,
                        values.email,
                        values.password
                    )
                },
                { registerWithEmailAndPasswordSuccess() },
                ::registerWithEmailAndPasswordError
            )
        }
    }

    private fun registerWithEmailAndPasswordSuccess() {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        controlRegisterSuccessVisibility()
    }

    private fun registerWithEmailAndPasswordError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
        when (error) {
            is EmailIsAlreadyUsed -> sendEvent(RegisterEvents.EmailIsAlreadyUsed)
            is NoInternetConnection -> sendEvent(RegisterEvents.NoInternetConnection)
            else -> sendEvent(RegisterEvents.SomeThingWentWrong)
        }
    }


    private fun validateFields(): Boolean {
        val values = state.value
        val name = values.name.validateRequireFields()
        val email = values.email.validateEmail()
        val password = values.password.validatePassword()
        val rePassword =
            values.passwordAgain.validatePassword() && values.password == values.passwordAgain
        val hasError = listOf(name, email, password, rePassword).contains(false)
        _state.update {
            it.copy(
                nameError = !name,
                emailError = !email,
                passwordError = !password,
                passwordAgainError = !rePassword
            )
        }
        return !hasError
    }
    override fun controlRegisterSuccessVisibility() {
        _state.update { it.copy(registerSuccessDialog = !it.registerSuccessDialog) }
    }

    override fun navigateToLogin() {
        sendEvent(RegisterEvents.NavigateToLogin)
    }
}
