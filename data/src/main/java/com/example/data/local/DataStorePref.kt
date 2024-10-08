package com.example.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")


class DataStorePref(private val context: Context) {

    companion object {
        private const val TAG = "DataStorePref"
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val USER_ID_KEY = stringPreferencesKey("USER_ID")
        val TOKEN_TYPE_KEY = stringPreferencesKey("TOKEN_TYPE")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")

        val ACCOUNT_NUMBER_KEY = stringPreferencesKey("ACCOUNT_NUMBER")
        val ACCOUNT_ID_KEY = stringPreferencesKey("ACCOUNT_ID")

        val ISLOGIN = booleanPreferencesKey("ISLOGIN")
    }

    val userName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY]
        }.catch { e ->
            Log.e(TAG, "Error fetching user name", e)
            emit(null)
        }

    val accessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching access token", e)
            emit(null)
        }

    val userId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching user ID", e)
            emit(null)
        }

    val refreshToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching refresh token", e)
            emit(null)
        }


    val tokenType: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_TYPE_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching token type", e)
            emit(null)
        }

    suspend fun storeLoginData(username: String, accessToken: String, userId: String, tokenType: String, refreshToken : String): Flow<Boolean> = flow {
        try {
            Log.d(TAG, "Storing login data: userId = $userId, accessToken = $accessToken, tokenType = $tokenType, refreshToken = $refreshToken")
            context.dataStore.edit { preferences ->
                preferences[USER_NAME_KEY] = username
                preferences[ACCESS_TOKEN_KEY] = accessToken
                preferences[USER_ID_KEY] = userId
                preferences[TOKEN_TYPE_KEY] = tokenType
                preferences[REFRESH_TOKEN_KEY] = refreshToken
                preferences[ISLOGIN] = true
            }
            Log.d(TAG, "Login data stored successfully")
            emit(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error storing login data", e)
            emit(false)
        }
    }.catch { e ->
        Log.e(TAG, "Error in flow while storing login data", e)
        emit(false)
    }



    val accountNumber: Flow<String?> = context.dataStore.data
        .map {
            preferences ->
            preferences[ACCOUNT_NUMBER_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching account number", e)
            emit(null)
        }

    val accountId: Flow<String?> = context.dataStore.data
        .map {
            preferences ->
            preferences[ACCOUNT_ID_KEY]
        }
        .catch { e ->
            Log.e(TAG, "Error fetching account ID", e)
        }

    val isLogin: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ISLOGIN] ?: false
        }
        .catch { e ->
            Log.e(TAG, "Error fetching login status", e)
        }

    suspend fun storeBankAccountData(
        accountNumber : String,
        accountId : String
        ): Flow<Boolean> = flow {
            try {
                Log.d(TAG, "Storing bank account data: accountNumber = $accountNumber, accountId = $accountId")
                context.dataStore.edit { preferences->
                    preferences[ACCOUNT_NUMBER_KEY] = accountNumber
                    preferences[ACCOUNT_ID_KEY] = accountId
                }
                Log.d(TAG, "Bank account data stored successfully")
                emit(true)
            }catch (e: Exception){
                Log.e(TAG, "Error storing bank account data", e)
                emit(false)
            }
    }.catch { e ->
        Log.e(TAG, "Error in flow while storing bank account data", e)
        emit(false)
    }

    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
            preferences[ISLOGIN] = false
        }
    }

    suspend fun saveLoginStatus(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ISLOGIN] = status
        }
    }



}
