package com.example.swiftbargain.data.repository

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
    //endregion
}