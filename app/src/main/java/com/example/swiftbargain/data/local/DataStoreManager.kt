package com.example.swiftbargain.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreManager.DATA_STORE_NAME)

class DataStoreManager(private val context: Context) {

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
        private const val USER_UID = "userUid"
    }
}