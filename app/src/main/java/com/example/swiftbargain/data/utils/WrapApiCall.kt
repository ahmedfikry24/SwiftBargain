package com.example.swiftbargain.data.utils

import com.example.swiftbargain.ui.base.EmailIsAlreadyUsed
import com.example.swiftbargain.ui.base.EmailIsNoVerified
import com.example.swiftbargain.ui.base.NoInternetConnection
import com.example.swiftbargain.ui.base.SomethingWentWrong
import com.example.swiftbargain.ui.base.UserNotFound
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

suspend fun <T> wrapApiCall(
    connectivityChecker: InternetConnectivityChecker,
    request: suspend () -> T
): T {
    return try {
        if (connectivityChecker()) {
            request()
        } else throw NoInternetConnection()
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        throw UserNotFound()
    } catch (e: EmailIsNoVerified) {
        throw EmailIsNoVerified()
    } catch (e: FirebaseAuthUserCollisionException) {
        throw EmailIsAlreadyUsed()
    } catch (e: NoInternetConnection) {
        throw NoInternetConnection()
    } catch (e: Exception) {
        throw SomethingWentWrong()
    }
}
