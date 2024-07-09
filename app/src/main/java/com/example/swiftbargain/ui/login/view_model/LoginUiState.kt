package com.example.swiftbargain.ui.login.view_model

import com.example.swiftbargain.ui.utils.ContentStatus

data class LoginUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val email: String = "",
    val emailError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null
) 
