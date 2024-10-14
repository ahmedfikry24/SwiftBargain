package com.example.swiftbargain.ui.login.view_model

import androidx.credentials.Credential

interface LoginInteractions {
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun loginWithEmailAndPassword()
    fun controlUnVerifiedEmailDialogVisibility()
    fun loginWithGoogle(credential: Credential)
    fun loginWithFaceBook(id: String)
    fun onChangeForgetPasswordEmail(email: String)
    fun onSendResetPasswordEmail()
    fun controlResetPasswordDialogVisibility()
    fun controlResetPasswordBottomSheetVisibility()
    fun onRegister()
}
