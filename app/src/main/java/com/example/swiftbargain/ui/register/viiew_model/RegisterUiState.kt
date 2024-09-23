package com.example.swiftbargain.ui.register.viiew_model

import com.example.swiftbargain.ui.utils.ContentStatus

data class RegisterUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val name: String = "",
    val nameError: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val passwordAgain: String = "",
    val passwordAgainError: Boolean = false,
    val registerSuccessDialog: Boolean = false
) 
