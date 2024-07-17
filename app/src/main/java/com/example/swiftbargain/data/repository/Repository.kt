package com.example.swiftbargain.data.repository

import android.content.Intent
import com.example.swiftbargain.data.utils.SignInResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    // region data store
    suspend fun getIsLogin(): Flow<Boolean>
    suspend fun setIsLogin(value: Boolean)
    suspend fun getUserUid(): Flow<String>
    suspend fun setUserUid(uid: String)
    // endregion

    // region auth
    suspend fun login(email: String, password: String): String
    suspend fun signInWithGoogleIntent(intent: Intent): SignInResult
    suspend fun signInWithGoogle(id: String): String
    //endregion
}