package com.example.swiftbargain.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreManager.DATA_STORE_NAME)

class DataStoreManager(private val context: Context) {

    private val isLoginKey = booleanPreferencesKey(IS_LOGIN)
    val isLogin: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[isLoginKey] ?: false
        }

    suspend fun setIsLogin(value: Boolean) {
        context.dataStore.edit {
            it[isLoginKey] = value
        }
    }

    private val userUidKey = stringPreferencesKey(USER_UID)
    val userUid: Flow<String>
        get() = context.dataStore.data.map {
            it[userUidKey] ?: ""
        }

    suspend fun setUserUid(uid: String) {
        context.dataStore.edit {
            it[userUidKey] = uid
        }
    }

    companion object {
        const val DATA_STORE_NAME = "dataStorePreference"
        private const val IS_LOGIN = "isLogin"
        private const val USER_UID = "userUid"
    }
}