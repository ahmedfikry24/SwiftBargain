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
    suspend fun loginEmailAndPassword(email: String, password: String): String
    suspend fun loginWithGoogleIntent(intent: Intent): SignInResult
    suspend fun loginWithGoogle(id: String): String
    suspend fun signInWithFacebook(id: String): String
    //endregion
}