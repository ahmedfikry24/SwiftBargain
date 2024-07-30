package com.example.swiftbargain.ui.base

abstract class BaseError(message: String? = null) : Exception(message)
class UserNotFound(message: String? = null) : BaseError(message)
class RegistrationFailed(message: String? = null) : BaseError(message)
class EmailIsAlreadyUsed(message: String? = null) : BaseError(message)
class NoInternetConnection(message: String? = null) : BaseError(message)
class SomethingWentWrong(message: String? = null) : BaseError(message)

