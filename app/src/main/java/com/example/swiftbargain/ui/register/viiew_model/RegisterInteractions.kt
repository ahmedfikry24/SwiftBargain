package com.example.swiftbargain.ui.register.viiew_model

interface RegisterInteractions {
    fun onChangeName(name: String)
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun onChangePasswordAgain(password: String)
    fun registerWithEmailAndPassword()
    fun registerWithGoogle()
    fun registerWithFacebook()
    fun onSignIn()
}
