package com.example.swiftbargain.data.utils

import com.example.swiftbargain.ui.base.NoInternetConnection
import com.example.swiftbargain.ui.base.SomethingWentWrong
import com.example.swiftbargain.ui.base.UserNotFound
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

suspend fun <T> wrapApiCall(
    connectivityChecker: InternetConnectivityChecker,
    request: suspend () -> T
): T {
    return try {
        if (connectivityChecker()) {
            request()
        } else throw NoInternetConnection()
    } catch (e: NoInternetConnection) {
        throw NoInternetConnection()
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        throw UserNotFound()
    } catch (e: Exception) {
        throw SomethingWentWrong()
    }
}
