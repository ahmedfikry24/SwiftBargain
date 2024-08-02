package com.example.swiftbargain.ui.login.view_model

import com.example.swiftbargain.ui.utils.ContentStatus

data class LoginUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val unVerifiedEmailDialog: Boolean = false,
    val forgetPasswordEmail: String = "",
    val forgetPasswordEmailError: Boolean = false,
    val resetPasswordDialog: Boolean = false,
)
