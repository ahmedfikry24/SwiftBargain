package com.example.swiftbargain.data.repository

import com.example.swiftbargain.data.local.DataStoreManager
import com.example.swiftbargain.data.utils.InternetConnectivityChecker
import com.example.swiftbargain.data.utils.wrapApiCall
import com.example.swiftbargain.ui.base.UserNotFound
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val auth: FirebaseAuth,
    private val connectivityChecker: InternetConnectivityChecker
) : Repository {

    override suspend fun getIsLogin() = dataStore.isLogin

    override suspend fun setIsLogin(value: Boolean) = dataStore.setIsLogin(value)

    override suspend fun getUserUid(): Flow<String> = dataStore.userUid

    override suspend fun setUserUid(uid: String) = dataStore.setUserUid(uid)

    override suspend fun login(email: String, password: String): String {
        val result = wrapApiCall(connectivityChecker) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
        return result.user?.uid ?: throw UserNotFound()
    }
}