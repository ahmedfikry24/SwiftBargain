package com.example.swiftbargain.ui.login.view_model

import android.content.Intent

interface LoginInteractions {
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun loginWithEmailAndPassword()
    fun controlUnVerifiedEmailDialogVisibility()
    fun getGoogleCredential(intent: Intent)
    fun loginWithFaceBook(id: String)
    fun onChangeForgetPasswordEmail(email: String)
    fun onSendResetPasswordEmail()
    fun controlResetPasswordDialogVisibility()
    fun onRegister()
}
