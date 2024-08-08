package com.example.swiftbargain.ui.login.view_model

sealed interface LoginEvents {
    data object NoInternetConnection : LoginEvents
    data object InvalidEmailOrPassword : LoginEvents
    data object CredentialFailed : LoginEvents
    data object EmailNotRegister : LoginEvents
    data object SomeThingWentWrong : LoginEvents
    data object LoginSuccess : LoginEvents
    data object NavToRegister : LoginEvents
}
