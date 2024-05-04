package com.jyproject.data.features.auth

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jyproject.domain.features.auth.repository.UserDataRepository
import com.jyproject.domain.models.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val context: Context
): UserDataRepository {
    private val Context.dataStore by preferencesDataStore(name = "user_data")
    private val _userData = MutableStateFlow(UserData())

    companion object{
        private val NUM_KEY = stringPreferencesKey("num")
        private val TOKEN_KEY = stringPreferencesKey("token")
    }

    override suspend fun getUserData(): UserData {
        val userData = context.dataStore.data
            .catch { exception ->
                if(exception is java.io.IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }.map { preference ->
                mapperToUserData(preference)
            }.first()
        return userData
    }

    override suspend fun setUserData(key: String, value: String) {
        context.dataStore.edit { preferences ->
            val preferKey = when(key){
                "num" -> {
                    _userData.value.userNum = value
                    NUM_KEY
                }
                "token" -> {
                    _userData.value.token = value
                    TOKEN_KEY
                }
                else -> throw IllegalStateException("Unknown key: $key")
            }
            preferences[preferKey] = value
        }
    }

    private fun mapperToUserData(preferences: Preferences): UserData{
        val num = preferences[NUM_KEY] ?: ""
        val token = preferences[TOKEN_KEY] ?: ""

        return UserData(num, token)
    }
}