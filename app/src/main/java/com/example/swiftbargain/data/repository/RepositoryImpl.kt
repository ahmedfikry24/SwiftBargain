package com.example.swiftbargain.data.repository

import com.example.swiftbargain.data.local.DataStoreManager
import com.example.swiftbargain.ui.base.InvalidAuthentication
import com.example.swiftbargain.ui.base.UserNotFound
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val auth: FirebaseAuth
) : Repository {

    override suspend fun getIsLogin() = dataStore.isLogin

    override suspend fun setIsLogin(value: Boolean) = dataStore.setIsLogin(value)

    override suspend fun login(email: String, password: String): String {
        try {
            val result =
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            return result.user?.uid ?: throw UserNotFound()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw UserNotFound()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidAuthentication()
        }
    }
}