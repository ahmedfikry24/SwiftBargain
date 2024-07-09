package com.example.swiftbargain.ui.login.view_model

interface LoginInteractions {
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun onClickSignIn()
    fun loginWithGoogle()
    fun loginWithFaceBook()
    fun onForgetPassword()
    fun onRegister()
}
