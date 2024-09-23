package com.example.swiftbargain.ui.register.viiew_model

sealed interface RegisterEvents {
    data object NavigateToLogin : RegisterEvents
    data object RegistrationFailed : RegisterEvents
    data object EmailIsAlreadyUsed : RegisterEvents
    data object NoInternetConnection : RegisterEvents
    data object SomeThingWentWrong : RegisterEvents
}
