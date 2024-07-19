package com.example.swiftbargain.ui.login.view_model

import android.content.Intent

interface LoginInteractions {
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun loginWithEmailAndPassword()
    fun getGoogleCredential(intent: Intent)
    fun loginWithFaceBook(id: String)
    fun onForgetPassword()
    fun onRegister()
}
