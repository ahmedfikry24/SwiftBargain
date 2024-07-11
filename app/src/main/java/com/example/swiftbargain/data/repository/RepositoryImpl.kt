package com.example.swiftbargain.data.repository

import com.example.swiftbargain.data.local.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager
) : Repository {

    override suspend fun getIsLogin(): Flow<Boolean> {
        return dataStore.isLogin
    }

    override suspend fun setIsLogin(value: Boolean) {
        return dataStore.setIsLogin(value)
    }
}