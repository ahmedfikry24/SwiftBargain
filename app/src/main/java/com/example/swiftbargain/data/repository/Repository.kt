package com.example.swiftbargain.data.repository

import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface Repository {
    // region data store
    suspend fun getUserUid(): Flow<String>
    suspend fun setUserUid(uid: String)
    // endregion

    // region auth
    suspend fun loginWithEmailAndPassword(email: String, password: String): String
    suspend fun signWithGoogleIntent(intent: Intent): String
    suspend fun loginWithGoogle(id: String): String
    suspend fun loginWithFacebook(id: String): String
    suspend fun resetPassword(email: String)
    suspend fun registerWithEmailAndPassword(name: String, email: String, password: String)
    //endregion
}