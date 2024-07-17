package com.example.swiftbargain.ui.login.view_model

import android.content.Intent

interface LoginInteractions {
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun onClickSignIn()
    fun getGoogleCredential(intent: Intent)
    fun loginWithFaceBook()
    fun onForgetPassword()
    fun onRegister()
}
