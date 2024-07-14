package com.example.swiftbargain.ui.base

abstract class BaseError(message: String? = null) : Exception(message)
class UserNotFound(message: String? = null) : BaseError(message)
class InvalidAuthentication(message: String? = null) : BaseError(message)
class NoInternetConnection(message: String? = null) : BaseError(message)
class SomethingWentWrong(message: String? = null) : BaseError(message)

